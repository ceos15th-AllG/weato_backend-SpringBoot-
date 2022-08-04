package allG.weato.domains.mail;

import allG.weato.domains.mail.dto.MailDto;
import allG.weato.domains.member.MemberService;
import allG.weato.domains.member.entities.Member;
import allG.weato.oauth2.JwtMemberDetails;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("/api")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final MemberService memberService;

    @PostMapping("/mail")
    public void sendValidateNum(@RequestBody @Valid MailDto mailDto){
        mailService.mailSend(mailDto);
    }


    @PatchMapping("/mail")
    public HttpStatus validateNewsletterEmail(@RequestBody @Valid Integer num){
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);

        if(num==findMember.getAuthNum()){
            memberService.confirmEmailValidation(findMember);
            return HttpStatus.ACCEPTED;
        }
        else return HttpStatus.FORBIDDEN;
    }
}
