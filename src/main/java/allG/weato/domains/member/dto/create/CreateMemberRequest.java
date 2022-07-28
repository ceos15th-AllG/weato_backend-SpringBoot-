package allG.weato.domains.member.dto.create;

import allG.weato.domains.enums.TagType;
import lombok.Data;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateMemberRequest {

    @Valid
    private String nickname;

    private String imageUrl;

    @Valid
    private String newsletterEmail;

    private Boolean drug;
    private Boolean sleep;
    private Boolean cleaning;
    private Boolean food;
    private Boolean environment;
    private Boolean etc;

}
