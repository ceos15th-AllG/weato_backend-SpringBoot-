package allG.weato.domains.newsletter.newsletterDto;

import allG.weato.domains.member.entities.Member;
import allG.weato.domains.newsletter.entities.Newsletter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkResponseDto {

    private String name;
    private String title;
    private int bookmarkCount;

    public BookmarkResponseDto(Member member, Newsletter newsletter){
        name=member.getName();
        title= newsletter.getTitle();
        bookmarkCount=newsletter.getBookMarkList().size();
    }


}
