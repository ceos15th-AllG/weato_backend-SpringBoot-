package allG.weato.domains.member.dto.retrieve;

import allG.weato.domains.enums.TagType;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.newsletter.newsletterDto.retrieve.NewsletterResponseDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookmarkResponseDto {

    String name;
    String email;
    List<NewsletterResponseDto> newsletterResponseDtos;

    public BookmarkResponseDto(Member member){
        name = member.getName();
        email=member.getNewsletterEmail();
        newsletterResponseDtos=member.getBookMarkList()
                .stream()
                .map(n -> new NewsletterResponseDto(n.getNewsletter()))
                .collect(Collectors.toList());
    }

    public BookmarkResponseDto(Member member, TagType tagType){
        name=member.getName();
        email=member.getNewsletterEmail();
        newsletterResponseDtos=member.getBookMarkList()
                .stream()
                .filter(n->n.getNewsletter().getTagType()==tagType)
                .map(n->new NewsletterResponseDto(n.getNewsletter()))
                .collect(Collectors.toList());
    }
}
