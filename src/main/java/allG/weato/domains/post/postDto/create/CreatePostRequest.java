package allG.weato.domains.post.postDto.create;

import allG.weato.domains.enums.BoardType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CreatePostRequest {
    @NotEmpty
    String title;

    @NotEmpty
    String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    LocalDateTime createAt;

    BoardType boardType;
    List<String> imageUrls = new ArrayList<>();

}
