package allG.weato.domains.member.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_id")
    private Long id;

    private String introduction;

    private String imageUrl;

    public void changeImgurl(String imageUrl){
        this.imageUrl=imageUrl;
    }
    public void changeIntroduction(String introduction){
        this.introduction=introduction;
    }
}
