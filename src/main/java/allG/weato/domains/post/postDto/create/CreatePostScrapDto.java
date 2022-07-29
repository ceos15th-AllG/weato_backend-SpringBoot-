package allG.weato.domains.post.postDto.create;

import allG.weato.domains.post.entities.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreatePostScrapDto {
    private String title;
    private int count;

    public CreatePostScrapDto(Post post){
        title=post.getTitle();
        count=post.getScrapList().size();
    }
}
