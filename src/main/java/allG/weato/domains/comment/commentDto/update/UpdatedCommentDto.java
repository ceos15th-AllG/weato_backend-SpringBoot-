package allG.weato.domains.comment.commentDto.update;

import allG.weato.domains.comment.entities.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
public class UpdatedCommentDto {

    String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime updatedAt;
    public UpdatedCommentDto(Comment comment){
        content=comment.getContent();
        updatedAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}



