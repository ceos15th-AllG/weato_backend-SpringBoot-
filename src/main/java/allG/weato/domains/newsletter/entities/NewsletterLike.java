package allG.weato.domains.newsletter.entities;

import allG.weato.domains.member.entities.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class NewsletterLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsletter_like_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newsletter_id")
    private Newsletter newsletter;

    public void initMember(Member member){
        this.member=member;
    }

    public void initNewsletter(Newsletter newsletter){
        this.newsletter=newsletter;
    }


}
