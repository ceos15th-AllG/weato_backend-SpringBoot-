package allG.weato.domains.post.postDto.retrieve;

import allG.weato.domains.post.entities.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostDto {
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private String author;

    private int commentsCounter;

    private int views;

    private int likeCounter;

    public PostDto(Post post) {
        title = post.getTitle();
        createdAt = post.getCreatedAt();
        author = post.getMember().getName();
        commentsCounter = post.getCommentList().size();
        views= post.getViews();
        likeCounter=post.getLikeCount();
    }
}
