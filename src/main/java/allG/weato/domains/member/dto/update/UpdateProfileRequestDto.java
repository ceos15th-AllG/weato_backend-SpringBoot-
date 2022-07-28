package allG.weato.domains.member.dto.update;

import allG.weato.domains.enums.SymptomDegree;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateProfileRequestDto {

    private String imageUrl;
    private String nickname;

    private Integer medicalHistory;
    private Boolean isRecurrence;
    private Boolean isFamilyHistory;

    private Boolean moisturizer;
    private Boolean steroid;
    private Boolean diet;
    private Boolean drug;
    private Boolean cleaning;
    private Boolean ointment;
    private Boolean laser;
    private Boolean orientalMedicine;
    private Boolean etc;

    private Boolean tagDrug;
    private Boolean tagSleep;
    private Boolean tagCleaning;
    private Boolean tagFood;
    private Boolean tagEnvironment;
    private Boolean otherwise;

    private SymptomDegree symptomDegree;

}
