package allG.weato.domains.member.dto;

import allG.weato.domains.enums.ManagementType;
import allG.weato.domains.enums.SymptomDegree;
import allG.weato.domains.member.entities.AdditionalInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AdditionalInfoResponseDto {
    private int medicalHistory;
    private Boolean familyHistory;
    private Boolean recurrence;
    private SymptomDegree symptomDegree;
    private List<ManagementType> managementTypes = new ArrayList<>();

    public AdditionalInfoResponseDto(AdditionalInfo additionalInfo){
        medicalHistory=additionalInfo.getMedicalHistory();
        familyHistory=additionalInfo.getIsFamilyHistory();
        recurrence=additionalInfo.getIsRecurrence();
        symptomDegree=additionalInfo.getSymptomDegree();
        managementTypes=additionalInfo.getManagementTypes();
    }
}
