package allG.weato.domains.member.dto.retrieve;

import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.postDto.retrieve.ScrapedPostDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ScrapRetrieveResponseDto {

    private int count;
    private List<ScrapedPostDto> scrapedPosts;

    public ScrapRetrieveResponseDto(Member member){
        count = member.getScrap().getPostList().size();
        scrapedPosts = member.getScrap().getPostList()
                .stream().map(p->new ScrapedPostDto(p)).collect(Collectors.toList());
    }
}
