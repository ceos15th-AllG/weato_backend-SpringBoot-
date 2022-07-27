//package allG.weato.domains.member.entities;
//
//import allG.weato.domains.newsletter.entities.Newsletter;
//import allG.weato.domains.enums.TagType;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@NoArgsConstructor
//public class Tag {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    private TagType tagType;
//
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "member_id")
////    private Member member;
//
////    @OneToMany(mappedBy = "tag" )
////    @JsonIgnore
////    private List<Newsletter> newsletters = new ArrayList<>();
//
//    public Tag(TagType tagType){
//        this.tagType=tagType;
//    }
//
////    public void setOwner(Member member){
////        this.member=member;
////    }
////
////    public void addNewsletter(Newsletter newsletter){
////        newsletters.add(newsletter);
////    }
//
//}
