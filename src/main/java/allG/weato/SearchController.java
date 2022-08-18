package allG.weato;

import allG.weato.domains.newsletter.NewsletterRepository;
import allG.weato.domains.newsletter.NewsletterService;
import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.newsletter.newsletterDto.retrieve.NewsletterRetrieveDto;
import allG.weato.domains.post.PostService;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.post.postDto.retrieve.PostDto;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchController {

    private final PostService postService;
    private final NewsletterService newsletterService;

    @GetMapping("/search")
    public SearchRequestDto searchPostsAndNewsletters(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "newsletter-page",defaultValue = "1") Integer newsletterPage,
            @RequestParam(value="post-page",defaultValue = "1")Integer postPage){
        Page<Newsletter> searchedNewsletter = newsletterService.searchNewslettersWithKeyword(newsletterPage-1,keyword);
        System.out.println("searchedNewsletter.getTotalElements() = " + searchedNewsletter.getTotalElements());
        List<Newsletter> newsletters = searchedNewsletter.getContent();
        System.out.println("newsletters.size() = " + newsletters.size());
        int newsletterLastPage = searchedNewsletter.getTotalPages();
        if(newsletterLastPage==0) newsletterLastPage++;
        int newsletterCurrent = newsletterPage;
        int newsletterMin = 1+newsletterCurrent/10*10;
        int newsletterMax =10+newsletterCurrent/10*10;
        if(newsletterMax>=newsletterLastPage) newsletterMax = newsletterLastPage;

        Page<Post> searchedPosts = postService.searchPostsWithKeyword(postPage-1,keyword);
        System.out.println("searchedPosts.getTotalElements() = " + searchedPosts.getTotalElements());
        List<Post> posts = searchedPosts.getContent();
        System.out.println("posts.size() = " + posts.size());
        int postLastPage = searchedPosts.getTotalPages();
        if(postLastPage==0) postLastPage++;
        int postCurrent = postPage;
        int postMin = 1+postCurrent/10*10;
        int postMax =10+postCurrent/10*10;
        if(postMax>=postLastPage) postMax = postLastPage;

        List<NewsletterRetrieveDto> newsletterDtos = newsletters
                .stream()
                .map(n -> new NewsletterRetrieveDto(n)).collect(Collectors.toList());

        List<PostDto> postDtos = posts
                .stream()
                .map(p -> new PostDto(p)).collect(Collectors.toList());

        return new SearchRequestDto(newsletterDtos,postDtos,
                newsletterMin,newsletterMax,newsletterCurrent,postMin,postMax,postCurrent);
    }


    @Data
    @AllArgsConstructor
    public static class SearchRequestDto<T1,T2>{
        private T1 newslettersData;
        private T2 postsData;
        private int newsletterMin;
        private int newsletterMax;
        private int newsletterCurrent;
        private int postMin;
        private int postMax;
        private int postCurrent;

    }
}
