package allG.weato.domains.post.postDto.retrieve;

import allG.weato.domains.enums.BoardType;
import allG.weato.domains.member.entities.Level;
import allG.weato.domains.post.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScrapedPostDto {

    private String title;
    private String name;
    private Level level;
    private BoardType boardType;
    private int likeCount;
    private int views;
    private int scrapedCount;


    public ScrapedPostDto(Post post){
        title = post.getTitle();
        name = post.getMember().getName();
        level=post.getMember().getLevel();
        boardType=post.getBoardType();
        likeCount= post.getLikeCount();
        views=post.getViews();
        scrapedCount=post.getScrapCount();
    }
}
