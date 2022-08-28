package allG.weato.domains.member.dto;

import allG.weato.domains.enums.BoardType;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.post.postDto.retrieve.PostRetrieveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class MemberScrapedPostDto {
    private int min;

    private int max;

    private int current;
    private List<PostRetrieveDto> result;


    public MemberScrapedPostDto(Member member,Pageable pageable){

        List<PostRetrieveDto> scrapedPosts=member.getScrapList()
                .stream()
                .map(scrap -> new PostRetrieveDto(scrap.getPost()))
                .collect(Collectors.toList());
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), scrapedPosts.size());
        Page<PostRetrieveDto> page = new PageImpl<>(scrapedPosts.subList(start, end), pageable, scrapedPosts.size());

        int lastPage = page.getTotalPages();
        result=page.getContent();
        current=pageable.getPageNumber()+1;
        min = 1+current/10*10;
        max =10+current/10*10;
        if(max>=lastPage) max = lastPage;
        if(max==0) max++;
    }
    public MemberScrapedPostDto(Member member,Pageable pageable, BoardType boardType){
        List<PostRetrieveDto> scrapedPosts=member.getScrapList()
                .stream()
                .filter(scrap -> scrap.getPost().getBoardType()==boardType)
                .map(scrap -> new PostRetrieveDto(scrap.getPost()))
                .collect(Collectors.toList());

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), scrapedPosts.size());
        Page<PostRetrieveDto> page = new PageImpl<>(scrapedPosts.subList(start, end), pageable, scrapedPosts.size());
        int lastPage = page.getTotalPages();
        result=page.getContent();
        current=pageable.getPageNumber()+1;
        min = 1+current/10*10;
        max =10+current/10*10;
        if(max==0) max++;
    }
}
