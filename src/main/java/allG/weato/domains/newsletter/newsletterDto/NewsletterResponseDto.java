package allG.weato.domains.newsletter.newsletterDto;

import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.enums.TagType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NewsletterResponseDto {
    private String title;
    private TagType tagType;
    private LocalDateTime createAt;

    public NewsletterResponseDto(Newsletter newsletter){
        title=newsletter.getTitle();
        tagType=newsletter.getTagType();
        createAt=newsletter.getCreatedAt();
    }
}
