package allG.weato.domains.post.entities;

import allG.weato.domains.post.entities.Post;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Scrap {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @OneToMany(mappedBy = "scrap")
    private List<Post> postList = new ArrayList<>();

    public void addPost(Post post){
        postList.add(post);
        post.scrapedBy(this);
        post.addScrapCount();
    }

}
