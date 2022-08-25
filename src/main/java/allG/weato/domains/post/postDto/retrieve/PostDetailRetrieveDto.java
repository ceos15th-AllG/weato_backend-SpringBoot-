package allG.weato.domains.post.postDto.retrieve;

import allG.weato.domains.post.entities.Post;
import allG.weato.domains.comment.commentDto.retrieve.CommentRetrieveDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostDetailRetrieveDto {
    String Author;
    String title;
    String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt;
    int likeCount;
    List<CommentRetrieveDto> comments;

    int views;

    int scrapCount;

    public PostDetailRetrieveDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.likeCount = post.getLikeCount();
        this.Author=post.getMember().getName();
        this.views=post.getViews();
        this.comments = post.getCommentList().stream()
                .map( c -> new CommentRetrieveDto(c)).collect(Collectors.toList());
        this.scrapCount=post.getScrapCount();
    }
}
