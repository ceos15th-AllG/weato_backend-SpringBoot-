package allG.weato.domains.newsletter.newsletterDto.retrieve;

import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.enums.TagType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NewsletterResponseDto {
    private String title;
    private TagType tagType;

    private int views;

    private int likeCount;
    private LocalDateTime createdAt;

    private Integer bookmarkCount;
    public NewsletterResponseDto(Newsletter newsletter){
        title=newsletter.getTitle();
        tagType=newsletter.getTagType();
        createdAt=newsletter.getCreatedAt();
        views=newsletter.getViews();
        likeCount= newsletter.getNewsletterLikeList().size();
        bookmarkCount=newsletter.getBookMarkList().size();
    }
}
