package allG.weato.domains.member.entities;

import allG.weato.domains.enums.ManagementType;
import allG.weato.domains.enums.SymptomDegree;
import allG.weato.domains.member.dto.AdditionalInfoRequestDto;
import allG.weato.domains.member.dto.update.UpdateProfileRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "additional_info_id")
    private Long id;

    private Integer medicalHistory;

    private Boolean isRecurrence;

    private Boolean isFamilyHistory;

    private SymptomDegree symptomDegree;

    @ElementCollection
    private List<ManagementType> managementTypes = new ArrayList<>();

    public void changeMedicalHistory(Integer medicalHistory){
        this.medicalHistory=medicalHistory;
    }

    public void changeManagement(AdditionalInfoRequestDto request){
        managementTypes.clear();
        if(request.getCleaning()) managementTypes.add(ManagementType.CLEANING);
        if(request.getSteroid()) managementTypes.add(ManagementType.STEROID);
        if(request.getDiet()) managementTypes.add(ManagementType.DIET);
        if(request.getDrug()) managementTypes.add(ManagementType.DRUG);
        if(request.getCleaning()) managementTypes.add(ManagementType.CLEANING);
        if(request.getOintment()) managementTypes.add(ManagementType.OINTMENT);
        if(request.getLaser()) managementTypes.add(ManagementType.LASER);
        if(request.getOrientalMedicine()) managementTypes.add(ManagementType.ORIENTALMEDICINE);
        if(request.getEtc()) managementTypes.add(ManagementType.ETC);
    }

    public void changeManagement(UpdateProfileRequestDto request){
        managementTypes.clear();
        if(request.getCleaning()) managementTypes.add(ManagementType.CLEANING);
        if(request.getSteroid()) managementTypes.add(ManagementType.STEROID);
        if(request.getDiet()) managementTypes.add(ManagementType.DIET);
        if(request.getDrug()) managementTypes.add(ManagementType.DRUG);
        if(request.getCleaning()) managementTypes.add(ManagementType.CLEANING);
        if(request.getOintment()) managementTypes.add(ManagementType.OINTMENT);
        if(request.getLaser()) managementTypes.add(ManagementType.LASER);
        if(request.getOrientalMedicine()) managementTypes.add(ManagementType.ORIENTALMEDICINE);
        if(request.getEtc()) managementTypes.add(ManagementType.ETC);
    }

}
