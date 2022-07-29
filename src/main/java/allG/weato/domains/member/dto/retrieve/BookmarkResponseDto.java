package allG.weato.domains.member.dto.retrieve;

import allG.weato.domains.enums.TagType;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.newsletter.newsletterDto.NewsletterResponseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
