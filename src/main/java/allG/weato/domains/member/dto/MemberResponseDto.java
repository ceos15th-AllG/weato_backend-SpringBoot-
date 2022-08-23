package allG.weato.domains.member.dto;

import allG.weato.domains.member.entities.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String birthYear;
    private LocalDateTime createdAT;

    public MemberResponseDto(Member member) {
        id = member.getId();
        name = member.getName();
        email=member.getEmail();
        gender= member.getGender();
        birthYear=member.getBirthyear();
        createdAT=member.getCreateAt();

    }
}
