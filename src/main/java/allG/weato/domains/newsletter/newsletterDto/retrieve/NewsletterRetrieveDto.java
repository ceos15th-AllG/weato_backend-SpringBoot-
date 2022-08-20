package allG.weato.domains.newsletter.newsletterDto.retrieve;

import allG.weato.domains.enums.TagType;
import allG.weato.domains.newsletter.entities.Newsletter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewsletterRetrieveDto {

    private String title;
    private TagType tagType;

    private int views;

    private String content;

    private int likeCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    private int bookmarkCount;

    public NewsletterRetrieveDto(Newsletter newsletter){
        title= newsletter.getTitle();
        tagType=newsletter.getTagType();
        views=newsletter.getViews();
        content=newsletter.getContent();
        likeCount= newsletter.getLikeCount();
        createdAt=newsletter.getCreatedAt();
        bookmarkCount=newsletter.getBookMarkCount();
    }
}
