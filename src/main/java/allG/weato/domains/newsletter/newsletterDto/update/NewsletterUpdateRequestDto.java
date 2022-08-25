package allG.weato.domains.newsletter.newsletterDto.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Data
@NoArgsConstructor
public class NewsletterUpdateRequestDto {
    private String updatedTitle;
    private String updatedContent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
}
