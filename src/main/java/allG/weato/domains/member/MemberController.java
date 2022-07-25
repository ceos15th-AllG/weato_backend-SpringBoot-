package allG.weato.domains.member;


import allG.weato.domains.member.MemberService;
import allG.weato.domains.member.entities.Member;
import allG.weato.oauth2.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000", exposedHeaders = "token")
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public void getUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("user name = "+principal.getUsername());
//        Member member = memberService.getUser(principal.getUsername());
//
//        return ApiResponse.success("user", member);
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
