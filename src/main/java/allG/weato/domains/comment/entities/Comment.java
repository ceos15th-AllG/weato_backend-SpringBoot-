package allG.weato.domains.comment.entities;

import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.entities.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private int likeCount=0;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @OneToMany(mappedBy = "comment",cascade = CascadeType.ALL)
    private List<CommentLike> commentLikeList = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();


    public Comment(String content, Member member, Post post) {
        this.content = content;
        this.member = member;
        this.post = post;
    }

    public void changeContent(String content){
        this.content=content;
        createdAt=LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public void setPost(Post post){
        this.post=post;
    }

    public void setMember(Member member){
        this.member=member;
    }

    public void addLike(CommentLike commentLike){
        getCommentLikeList().add(commentLike);
        commentLike.setOwnComment(this);
        likeCount++;
    }

    public void deleteLike(CommentLike commentLike){
        commentLikeList.remove(commentLike);
        commentLike.setOwner(null);
        likeCount--;
    }


}
