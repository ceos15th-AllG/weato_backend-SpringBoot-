package allG.weato.domains.mail;

import allG.weato.domains.mail.dto.ReceiveAuthNumDto;
import allG.weato.domains.mail.dto.SendingMailDto;
import allG.weato.domains.member.MemberService;
import allG.weato.domains.member.entities.Member;
import allG.weato.oauth2.JwtMemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MailController {

    private final MailService mailService;
    private final MemberService memberService;


    @Operation(summary = "send validation-checking mail to mebmer", description = "뉴스레터 인증 메일 발송")
    @PostMapping("/mail")
    public void sendValidateNum(@RequestBody @Valid SendingMailDto sendingMailDto){
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);
        int num =  (int)(Math.random() * (99999 - 10000 + 1)) + 10000;
        memberService.emailValidation(findMember, num);
        mailService.mailSend(sendingMailDto, num);
    }


    @Operation(summary = "check the authorzation_code end update member ", description = "인증 코드 확인")
    @PatchMapping("/mail")
    public HttpStatus validateNewsletterEmail(@RequestBody @Valid ReceiveAuthNumDto num){
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();
        Member findMember = memberService.findByEmail(email);
        int number =num.getNum();
        if(number==findMember.getAuthNum()){
            memberService.emailValidation(findMember);
            return HttpStatus.ACCEPTED;
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
