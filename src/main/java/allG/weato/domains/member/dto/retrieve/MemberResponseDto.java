package allG.weato.domains.member.dto.retrieve;

import allG.weato.domains.member.entities.Level;
import allG.weato.domains.member.entities.Member;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponseDto {
    private Long id;

    private Level level;
    private String name;
    private String email;
    private String gender;
    private String birthYear;
    private LocalDateTime createdAT;

    public MemberResponseDto(Member member) {
        id = member.getId();
        name = member.getName();
        level=member.getLevel();
        email=member.getEmail();
        gender= member.getGender();
        birthYear=member.getBirthyear();
        createdAT=member.getCreateAt();

    }
}
