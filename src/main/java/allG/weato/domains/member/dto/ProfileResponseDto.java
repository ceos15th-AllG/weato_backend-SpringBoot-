package allG.weato.domains.member.dto;

import allG.weato.domains.enums.ManagementType;
import allG.weato.domains.enums.ProviderType;
import allG.weato.domains.enums.SymptomDegree;
import allG.weato.domains.member.entities.Level;
import allG.weato.domains.member.entities.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {

    private String name;
    private String newsletterEmail;
    private String imageUrl;
    private int level;
    private String nickname;
    private String birthyear;
    private ProviderType providerType;

    private Integer medicalHistory;
    private SymptomDegree symptomDegree;
    private List<ManagementType> managementTypeList=new ArrayList<>();

    public ProfileResponseDto(Member member){
        name = member.getName();
        newsletterEmail= member.getNewsletterEmail();
        imageUrl=member.getProfile().getImageUrl();
        level=member.getLevel().getLevel();
        nickname=member.getNickname();
        birthyear=member.getBirthyear();
        providerType=member.getProviderType();
        medicalHistory=member.getAdditionalInfo().getMedicalHistory();
        symptomDegree=member.getAdditionalInfo().getSymptomDegree();
        managementTypeList=member.getAdditionalInfo().getManagementTypes();
    }

}
