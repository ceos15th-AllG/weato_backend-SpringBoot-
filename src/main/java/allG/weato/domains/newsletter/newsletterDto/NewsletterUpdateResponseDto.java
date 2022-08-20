package allG.weato.domains.newsletter.newsletterDto;

import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.enums.TagType;
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
