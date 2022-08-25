package allG.weato.domains.newsletter.newsletterDto.update;

import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.enums.TagType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Data
@NoArgsConstructor
public class NewsletterUpdateResponseDto {
    private String title;
    private String content;
    private TagType tagType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    private int likeCount=0;

    public NewsletterUpdateResponseDto(Newsletter newsletter)
    {
        title=newsletter.getTitle();
        content = newsletter.getContent();
        tagType=newsletter.getTagType();
        createdAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        likeCount=newsletter.getLikeCount();
    }
}
