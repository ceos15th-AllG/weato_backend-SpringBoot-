package allG.weato.domains.comment;

import allG.weato.config.auth.dto.SessionMember;
import allG.weato.domains.comment.commentDto.delete.DeleteCommentLikeDto;
import allG.weato.domains.comment.entities.Comment;
import allG.weato.domains.comment.entities.CommentLike;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.entities.Post;
import allG.weato.dto.AddLikeDto;
import allG.weato.domains.comment.commentDto.create.CreateCommentRequest;
import allG.weato.domains.comment.commentDto.create.CreateCommentResponse;
import allG.weato.domains.comment.commentDto.update.UpdatedCommentDto;
import allG.weato.domains.member.MemberService;
import allG.weato.domains.post.PostService;
import allG.weato.oauth2.JwtMemberDetails;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.RestException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentsController {
    private final PostService postService;
    private final MemberService memberService;
    private final HttpSession httpSession;

    private final CommentService commentService;

    @Operation(summary = "add comment to post", description = "댓글 생성")
    @PostMapping("/api/posts/{id}/comments")
    public CreateCommentResponse addComment(@PathVariable("id") Long id, @RequestBody @Valid CreateCommentRequest request)
    {
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember=memberService.findByEmail(email);
        Post post = postService.findPostById(id);
        if(post==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        Comment comment = new Comment();
        comment.changeContent(request.getContent());
        postService.addComment(post,comment);
        memberService.addComment(findMember,comment);
        return new CreateCommentResponse(comment);
    }



    @Operation(summary = "update specific comment", description = "댓글 수정")
    @PatchMapping("/api/posts/{postId}/comments/{commentId}")
    public UpdatedCommentDto updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,String content){
        Comment comment = commentService.findCommentById(commentId);
        if(comment==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        commentService.updateComment(commentId,content);
        return new UpdatedCommentDto(comment);
    }

    @Operation(summary = "delete specific comment", description = "댓글 삭제")
    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    public HttpStatus deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId){
        Comment comment = commentService.findCommentById(commentId);
        if(comment==null) throw new RestException(CommonErrorCode.RESOURCE_NOT_FOUND);
        commentService.deleteComment(comment);
        return HttpStatus.NO_CONTENT;
    }

    @Operation(summary = "likes to comment", description = "댓글 좋아요")
    @PostMapping("/api/posts/{postId}/comments/{commentId}/likes")
    public AddLikeDto addLikes(@PathVariable("postId") Long postId,
                               @PathVariable("commentId") Long commentId){

        Comment findComment = commentService.findCommentById(commentId);
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);
        CommentLike commentLike = new CommentLike();
        List<CommentLike> commentLikes = findComment.getCommentLikeList();
        for (CommentLike like : commentLikes) {
            if(like.getMember().getId()==findMember.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already liked it");
            }
        }
        commentService.addCommentLike(findMember,findComment,commentLike);
        return new AddLikeDto(findComment.getId(),findComment.getLikeCount());
    }

    @Operation(summary = "cancel likes to comment", description = "댓글 좋아요 취소")
    @DeleteMapping("/api/posts/{postId}/comments/{commentId}/likes")
    public DeleteCommentLikeDto deleteCommentLike(@PathVariable("postId") Long postId,
                                                  @PathVariable("commentId") Long commentId){
        Comment comment = commentService.findCommentById(commentId);
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);
        for(CommentLike like: comment.getCommentLikeList()){
            if(like.getMember().getId()== findMember.getId()){
                commentService.deleteCommentLike(findMember,comment,like);
                break;
            }
        }
        return new DeleteCommentLikeDto(comment);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        int count;
        private T data;
    }
}
