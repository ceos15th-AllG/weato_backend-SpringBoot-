package allG.weato.domains.post;

import allG.weato.domains.enums.BoardType;
import allG.weato.domains.post.entities.Attachment;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.post.entities.PostLike;
import allG.weato.domains.post.entities.Scrap;
import allG.weato.domains.post.postDto.create.CreatePostScrapDto;
import allG.weato.dto.AddLikeDto;
import allG.weato.domains.post.postDto.create.CreatePostRequest;
import allG.weato.domains.post.postDto.create.CreatePostResponse;
import allG.weato.domains.post.postDto.retrieve.PostDetailDto;
import allG.weato.domains.post.postDto.retrieve.PostDto;
import allG.weato.dto.error.Error400;
import allG.weato.dto.error.Error404;
import allG.weato.dto.error.Error500;
import allG.weato.domains.post.postDto.update.UpdatePostRequest;
import allG.weato.domains.post.postDto.update.UpdatePostResponse;
import allG.weato.domains.member.MemberService;
import allG.weato.oauth2.JwtMemberDetails;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final MemberService memberService;
    private final PostService postService;
    private final HttpSession httpSession;


    @Operation(summary = "get all posts", description = "게시글 전체조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST", content = @Content(schema = @Schema(implementation =Error400.class ))),
            @ApiResponse(responseCode = "404",description = "NOT FOUND", content = @Content(schema = @Schema(implementation =Error404.class ))),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation =Error500.class )))
    })
    @GetMapping("/posts")
    public ResultForPaging showPosts(@RequestParam(value="type",defaultValue = "all") String type
            ,@RequestParam(value = "page",defaultValue = "1") Integer page) {

        BoardType boardType=BoardType.valueOf(type.toUpperCase());
        Page<Post> findPosts;

        if(boardType==BoardType.ALL) {
            findPosts = postService.findPostWithPaging(page-1);
        }
        else {
            findPosts = postService.findPostPageWithBoardType(page-1,boardType);
        }

        if(findPosts.isEmpty()) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);

        List<Post> posts = findPosts.getContent();
        int lastPageNum = findPosts.getTotalPages();
        int current = page;
        int min = 1+current/10*10;
        int max =10+current/10*10;
        if(max>=lastPageNum) max = lastPageNum;
        List<PostDto> result = posts.stream()
                .map(p -> new PostDto(p))
                .collect(Collectors.toList());

        return new ResultForPaging(result, min, max, current);
    }




    @Operation(summary = "Create posts", description = "게시글 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "created successfully", content = @Content(schema = @Schema(implementation =CreatePostResponse.class ))),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = Error400.class ))),
            @ApiResponse(responseCode = "404",description = "NOT FOUND", content = @Content(schema = @Schema(implementation =Error404.class ))),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = Error500.class )))
    })
    @PostMapping("/posts")
    public CreatePostResponse createPost(@RequestBody @Valid CreatePostRequest request) {
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);
        Post post = new Post(request.getTitle(),request.getContent(),request.getBoardType(),LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        post.setOwner(findMember);
        for (String  s : request.getImageUrls()) {
            Attachment attachment = new Attachment(s);
            post.addAttachments(attachment);
        }
        postService.save(post);
        CreatePostResponse response = new CreatePostResponse(post);
        return response;
    }

    @Operation(summary = "get specific post", description = "게시글 단건조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404",description = "NOT FOUND"),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/posts/{id}")
    public PostDetailDto showPost(@PathVariable("id") Long id) {
      Post post = postService.findPostById(id);
      if(post==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
      post.addViews();
      postService.save(post);
      PostDetailDto postDetailDto = new PostDetailDto(post);

      return postDetailDto;
    }


    @Operation(summary = "update post", description = "게시글 수정")
    @PatchMapping("/posts/{id}")
    public UpdatePostResponse updatePost(@PathVariable("id") Long id, @RequestBody @Valid UpdatePostRequest request){
       Post post = postService.findPostById(id);
       if(post==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        if(request.getTitle()!=null&&request.getContent()==null){
           postService.updatePostTitle(post, request.getTitle());
       } else if (request.getContent() != null &&request.getTitle()==null) {
           postService.updatePostContent(post, request.getContent());
       }else postService.updatePost(post, request.getTitle(), request.getContent());

        UpdatePostResponse updatePostResponse = new UpdatePostResponse(id, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        return updatePostResponse;
    }
    @Operation(summary = "delete post", description = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204",description = "Ok"),
            @ApiResponse(responseCode = "400",description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = Error400.class ))),
            @ApiResponse(responseCode = "404",description = "NOT FOUND", content = @Content(schema = @Schema(implementation =Error404.class ))),
            @ApiResponse(responseCode = "500",description = "INTERNAL SERVER ERROR", content = @Content(schema = @Schema(implementation = Error500.class )))
    })
    @DeleteMapping("/posts/{id}")
    public HttpStatus deletePost(@PathVariable("id") Long id)
    {
        Post post = postService.findPostById(id);
        if(post==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        postService.deletePost(post);
        return HttpStatus.NO_CONTENT;
    }

    @Operation(summary = "likes to post", description = "게시글 좋아요")
    @PostMapping("/posts/{postId}/likes")
    public AddLikeDto likeToPost(@PathVariable("postId") Long id){

        Post post = postService.findPostById(id);
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);
        if(findMember==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        PostLike postLike = new PostLike();
        List<PostLike> postLikeList = post.getPostLikeList();
        for (PostLike like : postLikeList) {
            if(like.getMember().getId()==findMember.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You already liked it");
            }
        }
        postService.addLike(findMember,post,postLike);
        return new AddLikeDto(post.getId(),post.getLikeCount());
    }

    @Operation(summary = "cancel likes to post", description = "게시글 좋아요 취소")
    @DeleteMapping("/posts/{postId}/likes")
    public HttpStatus deleteLike(@PathVariable("postId")Long id){

        Post findPost = postService.findPostById(id);
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);
        for (PostLike like : findPost.getPostLikeList()) {
            if(like.getMember().getId()==findMember.getId()){
                postService.deleteLike(findMember,findPost,like);
                break;
            }
        }
        return HttpStatus.NO_CONTENT;
    }

    @PostMapping("/posts/{postId}/scrap")
    public CreatePostScrapDto addScrap(@PathVariable("postId")Long postId){
        Post post = postService.findPostById(postId);

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);

        Scrap scrap = new Scrap();
        List<Scrap> scraps = post.getScrapList();
        for (Scrap scrap1 : scraps) {
            if(scrap1.getMember().getId()==member.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You already scraped it");
            }
        }
        postService.scrapPost(member,post,scrap);

        return new CreatePostScrapDto(post);
    }

    @DeleteMapping("posts/{postId}/scrap")
    public HttpStatus deleteScrap(@PathVariable("postId") Long postId){
        Post post = postService.findPostById(postId);


        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);

        for(Scrap scrap : post.getScrapList()){
            if(scrap.getMember().getId()==member.getId()){
                postService.deleteScrap(member,post,scrap);
                break;
            }
        }
        return HttpStatus.NO_CONTENT;

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


