package allG.weato.domains.member.dto;

import allG.weato.domains.enums.BoardType;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.postDto.retrieve.PostRetrieveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MemberScrapedPostDto {
    private int count;
    private List<PostRetrieveDto> scrapedPosts;

    public MemberScrapedPostDto(Member member){

        scrapedPosts=member.getScrapList()
                .stream()
                .map(scrap -> new PostRetrieveDto(scrap.getPost()))
                .collect(Collectors.toList());

        count = scrapedPosts.size();
    }
    public MemberScrapedPostDto(Member member, BoardType boardType){
        scrapedPosts=member.getScrapList()
                .stream()
                .filter(scrap -> scrap.getPost().getBoardType()==boardType)
                .map(scrap -> new PostRetrieveDto(scrap.getPost()))
                .collect(Collectors.toList());

        count=scrapedPosts.size();
    }
}
