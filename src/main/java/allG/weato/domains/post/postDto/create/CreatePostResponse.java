package allG.weato.domains.post.postDto.create;

import allG.weato.domains.post.entities.Post;
import allG.weato.domains.enums.BoardType;
import allG.weato.domains.post.postDto.AttachmentDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CreatePostResponse {

    String Author;
    String title;
    String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createdAt;
    BoardType boardType;
    List<AttachmentDto> attachments = new ArrayList<>();

    public CreatePostResponse(Post post){
        Author = post.getMember().getName();
        title=post.getTitle();
        content = post.getContent();
        createdAt = post.getCreatedAt();
        boardType=post.getBoardType();
        attachments=post.getAttachmentList().stream()
                .map(a -> new AttachmentDto(a)).collect(Collectors.toList());
    }
}
