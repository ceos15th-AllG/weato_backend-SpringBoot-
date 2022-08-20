package allG.weato.domains.newsletter.entities;

import allG.weato.domains.member.entities.BookMark;
//import allG.weato.domains.member.entities.Tag;
import allG.weato.domains.enums.TagType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Newsletter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsletter_id")
    private Long id;

    private String title; //뉴스레터 제목

    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createdAt; //작성일시

    private int likeCount = 0 ;

    private int bookMarkCount=0;

    private int views=0;


    @OneToMany(mappedBy = "newsletter",cascade = CascadeType.ALL) //단뱡힝이지만 양방향으로 처리. 역추적 할 일 없음.
    private List<BookMark> bookMarkList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TagType tagType;

    @OneToMany(mappedBy = "newsletter",cascade = CascadeType.ALL)
    private List<NewsletterLike> newsletterLikeList=new ArrayList<>();


    public Newsletter(String title, String content, TagType tagType){
        this.title=title;
        this.content=content;
        this.tagType=tagType;
        createdAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void setTagType(TagType tagType){
        this.tagType=tagType;
    }

    public void addBookMark(BookMark bookMark) {
        bookMarkList.add(bookMark);
        bookMark.initNewsletter(this);
        bookMarkCount++;
    }

//    public void classifyByTag(Tag tag){
//        this.tag=tag;
//        tag.addNewsletter(this);
//    }

    public void changeTitle(String title) {
        this.title=title;
    }

    public void changeContent(String content){
        this.content=content;
    }

    public void changeCreatedAt(LocalDateTime updatedAt){
        createdAt=updatedAt;
    }

    public void deleteBookMark(BookMark bookMark){
        bookMarkList.remove(bookMark);
        bookMark.initNewsletter(null);
        bookMarkCount--;
    }
    public void addViews() {
        views++;
    }

    public void addNewsletterLike(NewsletterLike newsletterLike){
        newsletterLikeList.add(newsletterLike);
        newsletterLike.initNewsletter(this);
        likeCount++;
    }

    public void deleteNewsletterLike(NewsletterLike newsletterLike){
        newsletterLikeList.remove(newsletterLike);
        newsletterLike.initNewsletter(null);
        likeCount--;
    }

}
