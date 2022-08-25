package allG.weato.domains.comment.commentDto.retrieve;

import allG.weato.domains.comment.entities.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentRetrieveDto {
    private Long id;
    private String author;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private int likeCounter;

    public CommentRetrieveDto(Comment comment){
        id=comment.getId();
        author=comment.getMember().getName();
        content=comment.getContent();
        createdAt=comment.getCreatedAt();
        likeCounter=comment.getLikeCount();
    }
}
