package allG.weato.domains.newsletter;

import allG.weato.domains.member.MemberService;
import allG.weato.domains.member.entities.BookMark;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.newsletter.entities.NewsletterLike;
import allG.weato.domains.newsletter.newsletterDto.BookmarkResponseDto;
import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.enums.TagType;
import allG.weato.domains.newsletter.newsletterDto.CreateNewsletterDto;
import allG.weato.domains.newsletter.newsletterDto.NewsletterResponseDto;
import allG.weato.domains.newsletter.newsletterDto.NewsletterUpdateRequestDto;
import allG.weato.domains.newsletter.newsletterDto.NewsletterUpdateResponseDto;
import allG.weato.domains.newsletter.newsletterDto.retrieve.NewsletterRetrieveDto;
import allG.weato.dto.AddLikeDto;
import allG.weato.oauth2.JwtMemberDetails;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsletterController {

    private final NewsletterService newsletterService;

    private final MemberService memberService;
    @Operation(summary = "get pages of newsletters with TagType", description = "태그 타입을 통한 뉴스레터 조회")
    @GetMapping("/newsletters") //얘도 마찬가지임 8개씩 페이징
    public ResultForPaging<Newsletter> findByTagType(@RequestParam(value="tag",defaultValue = "all") String code,
                                                     @RequestParam(value="page",defaultValue = "1") Integer page){
        TagType tagType = TagType.valueOf(code.toUpperCase());
        Page<Newsletter> finds;
        if(tagType==TagType.ALL) {
            finds = newsletterService.findPage(page-1);
        }
        else {
            finds = newsletterService.findPageByTag(tagType, page - 1);
        }
        List<Newsletter> newsletters = finds.getContent();

        if(newsletters.isEmpty()) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);

        int lastPage = finds.getTotalPages();
        int current = page;
        int min = 1+current/10*10;
        int max =10+current/10*10;
        if(max>=lastPage) max = lastPage;
        List<NewsletterResponseDto> result = newsletters
                .stream()
                .map(n -> new NewsletterResponseDto(n)).collect(toList());
        return new ResultForPaging(result,min,max,current);
    }

    @Operation(summary = "get specific newsletter", description = "뉴스레터 단건조회")
    @GetMapping("/newsletters/{id}") //단건 조회
    public NewsletterRetrieveDto findNewsletter(@PathVariable("id") Long id){
        Newsletter findOne = newsletterService.findOneById(id);
        findOne.addViews();
        newsletterService.save(findOne);
        return new NewsletterRetrieveDto(findOne);

    }

    @Operation(summary = "create a newsletter - only admin is authorized", description = "뉴스레터 생성")
    @PostMapping("/newsletters")
    public NewsletterResponseDto postNewsletter(@RequestBody @Valid CreateNewsletterDto request){
        Newsletter newsletter = new Newsletter(request.getTitle(),request.getContent(),request.getTagType());
        newsletterService.save(newsletter);
        /*Newsletter findOne = newsletterService.findOneById(id); //이거 약간 테스트처럼 드가는데???
        if(findOne==null) throw new RestException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        else */ return new NewsletterResponseDto(newsletter);
    }
    @Operation(summary = "update specific newsletter - only admin is authorized", description = "뉴스레터 수정")
    @PatchMapping("/newsletters/{id}") //업데이트
    public NewsletterUpdateResponseDto updateNewsletter(@PathVariable("id") Long id, @RequestBody @Valid NewsletterUpdateRequestDto request){
        Newsletter findOne = newsletterService.findOneById(id);
        if(findOne==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        newsletterService.updateNewsletter(findOne,request);
        return new NewsletterUpdateResponseDto(findOne);
    }

    @Operation(summary = "delete specific newsletter - only admin is authorized", description = "뉴스레터 삭제")
    @DeleteMapping("/newsletters/{id}") //삭제
    public HttpStatus deleteNewsletter(@PathVariable("id") Long id){
        Newsletter findNewsletter = newsletterService.findOneById(id);
        if(findNewsletter==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        newsletterService.deleteNewsletter(findNewsletter);
        return  HttpStatus.NO_CONTENT;
    }

    @Operation(summary = "bookmarks to newsletter", description = "뉴스레터에 북마크")
    @PostMapping("/newsletters/{id}/bookmark")
    public BookmarkResponseDto addBookmark(@PathVariable("id")Long id){
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);

        Newsletter newsletter = newsletterService.findOneById(id);

        BookMark bookMark = new BookMark();

        List<BookMark> bookMarks = newsletter.getBookMarkList();
        for (BookMark marks : bookMarks) {
            if(marks.getMember().getId()==findMember.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You already bookMarked it");
            }
        }
        newsletterService.addBookMark(findMember,newsletter,bookMark);

        return new BookmarkResponseDto(findMember,newsletter);
    }

    @Operation(summary = "delete bookmark to newsletter", description = "북마크 해제")
    @DeleteMapping("/newsletters/{id}/bookmark")
    public BookmarkResponseDto deleteBookmark(@PathVariable("id") Long id){
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);

        Newsletter newsletter = newsletterService.findOneById(id);

        List<BookMark> bookMarks = newsletter.getBookMarkList();
        for (BookMark bookMark : bookMarks) {
            if(bookMark.getNewsletter().getId()==newsletter.getId()){
                newsletterService.deleteBookMark(findMember,newsletter,bookMark);
                break;
            }
        }
        return new BookmarkResponseDto(findMember,newsletter);
    }

    @Operation(summary = "likes to newsletter", description = "뉴스레터 좋아요")
    @PostMapping("/newsletters/{id}/likes")
    public AddLikeDto addNewsletterLike(@PathVariable("id")Long id){
        Newsletter newsletter = newsletterService.findOneById(id);

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);

        List<NewsletterLike> newsletterLikeList = newsletter.getNewsletterLikeList();
        for (NewsletterLike newsletterLike : newsletterLikeList) {
            if(newsletterLike.getMember().getId()==findMember.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You already scraped it");
            }
        }

        NewsletterLike newsletterLike = new NewsletterLike();
        newsletterService.addNewsletterLike(findMember,newsletter,newsletterLike);

        return new AddLikeDto(newsletter.getId(),newsletter.getNewsletterLikeList().size());
    }

    @Operation(summary = "delete likes to comment", description = "댓글 좋아요 삭제")
    @DeleteMapping("/newsletters/{id}/likes")
    public HttpStatus deleteNewsletterLike(@PathVariable("id") Long id){
        Newsletter newsletter = newsletterService.findOneById(id);

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);

        List<NewsletterLike> newsletterLikeList = newsletter.getNewsletterLikeList();
        for (NewsletterLike newsletterLike : newsletterLikeList) {
            if(newsletterLike.getMember().getId()==findMember.getId()){
                newsletterService.deleteNewsletterLike(findMember,newsletter,newsletterLike);
                break;
            }
        }
        return HttpStatus.NO_CONTENT;
    }

    @GetMapping("/newsletters/search")
    public ResultForPaging searchNewslettersByKeyword(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "page",defaultValue = "1") Integer page
    ){
        Page<Newsletter> searchedNewsletters = newsletterService.searchNewslettersWithKeyword(page-1,keyword);
        List<Newsletter> newsletters = searchedNewsletters.getContent();

        if(newsletters.isEmpty()) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);

        int lastPage = searchedNewsletters.getTotalPages();
        int current = page;
        int min = 1+current/10*10;
        int max =10+current/10*10;
        if(max>=lastPage) max = lastPage;
        List<NewsletterResponseDto> result = newsletters
                .stream()
                .map(n -> new NewsletterResponseDto(n)).collect(toList());
        return new ResultForPaging(result,min,max,current);
    }

    @Data
    @AllArgsConstructor
    static class ResultForPaging<T>{
        private T data;
        private int min;
        private int max;
        private int current;
    }
}
