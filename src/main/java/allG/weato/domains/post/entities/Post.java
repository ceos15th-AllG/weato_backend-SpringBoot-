package allG.weato.domains.post.entities;

import allG.weato.domains.comment.entities.Comment;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.enums.BoardType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Post {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private LocalDateTime createAt;

    private String title;
    private String content;
    private int likeCount ;

    private int scrapCount;

    private int views;
    @ManyToOne(fetch = FetchType.LAZY) //멤버 - 게시글 외래키의 주인 > post
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;


    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL) //persist의 전파.
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Attachment> attachmentList = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<PostLike> postLikeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "scrap_id")
    private Scrap scrap;



    public int getLikeCount(){
        likeCount=getPostLikeList().size();
        return likeCount;
    }
    public Post(String title, String content,BoardType boardType,LocalDateTime createAt){
        this.title=title;
        this.content=content;
        this.boardType=boardType;
        this.createAt=createAt;
    }

    //setter 대용 메소드
    public void changeContent(String content){
        this.content=content;
        createAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void changeTitle(String title)
    {
        this.title=title;
        createAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }


    //연관관계 편의 메소드
    public void setOwner(Member member){
        this.member=member;
        member.getPostList().add(this);
        member.getLevel().addExp(10);
    }

    public void addComment(Comment comment){ // 완료
        this.getCommentList().add(comment);
        comment.setPost(this);
    }

    public void addAttachments(Attachment attachment){ //완료
        attachmentList.add(attachment);
        attachment.setPost(this);
    }

    public void addLike(PostLike postLike) { //완료
        this.postLikeList.add(postLike);
        postLike.setOwnPost(this);
        likeCount++;
    }

    public void deleteLike(PostLike postLike){
        this.postLikeList.remove(postLike);
        postLike.setOwnPost(null);
        likeCount--;
    }

    public void addViews(){
        this.views++;
    }

    public void scrapedBy(Scrap scrap){
        this.scrap=scrap;
    }

    public void deleteComment(Comment comment) {
        if(commentList.contains(comment)) commentList.remove(comment);
        else throw new IllegalStateException("존재하지 않는 댓글입니다");
    }

    public void addScrapCount() {
        scrapCount++;
    }
}
