package allG.weato.domains.member;


import allG.weato.domains.member.MemberService;
import allG.weato.domains.member.dto.MemberResponseDto;
import allG.weato.domains.member.entities.Member;
import allG.weato.oauth2.JwtMemberDetails;
import allG.weato.oauth2.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/v1/users")
    public MemberResponseDto getUser() {

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);

        return new MemberResponseDto(member);
    }


    @GetMapping("/members/{memberId}")// my-page
    public void showMember(@PathVariable("memberId") Long memberId){
        
    }
    @GetMapping("/members/{memberId}/profile")//프로필
    public void showMemberProfile(@PathVariable("memberId") Long memberId){

    }
    @GetMapping("/members/{memberId}/additional-info")//추가정보
    public void showMemberAdditionalInfo(@PathVariable("memberId") Long memberId){

    }
    @PostMapping("/members/{memberId}/additional-info")//추가정보
    public void createMemberAdditionalInfo(@PathVariable("memberId") Long memberId, @RequestBody @Valid int a){
    }

    @GetMapping("/members/{memberId}/bookmark") //북마크
    public void showBookMark(@PathVariable("memberId") Long memberId){

    }




    
    
    
}
