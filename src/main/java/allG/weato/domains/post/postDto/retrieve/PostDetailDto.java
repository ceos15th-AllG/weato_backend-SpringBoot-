package allG.weato.domains.post.postDto.retrieve;

import allG.weato.domains.post.entities.Post;
import allG.weato.domains.comment.commentDto.CommentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostDetailDto {
    String Author;
    String title;
    String content;
    LocalDateTime createdAt;
    int likeCount;
    List<CommentDto> comments;

    int views;

    int scrapCount;

    public PostDetailDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.likeCount = post.getLikeCount();
        this.Author=post.getMember().getName();
        this.views=post.getViews();
        this.comments = post.getCommentList().stream()
                .map( c -> new CommentDto(c)).collect(Collectors.toList());
        this.scrapCount=post.getScrapList().size();
    }
}
