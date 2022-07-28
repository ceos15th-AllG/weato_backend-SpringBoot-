package allG.weato.domains.member.dto.create;

import allG.weato.domains.enums.TagType;
import allG.weato.domains.member.entities.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberResponse {

    private Long id ;
    private String birthyear;
    private String newsletterEmail;
    private List<TagType> tagTypes = new ArrayList<>();

    public CreateMemberResponse(Member member){
        id= member.getId();
        birthyear=member.getBirthyear();
        newsletterEmail= member.getNewsletterEmail();
        tagTypes=member.getTagTypeList();
    }
}
