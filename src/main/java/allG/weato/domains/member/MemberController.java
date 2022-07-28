package allG.weato.domains.member;


import allG.weato.domains.enums.TagType;
import allG.weato.domains.member.MemberService;
import allG.weato.domains.member.dto.AdditionalInfoRequestDto;
import allG.weato.domains.member.dto.AdditionalInfoResponseDto;
import allG.weato.domains.member.dto.MemberResponseDto;
import allG.weato.domains.member.dto.ProfileResponseDto;
import allG.weato.domains.member.dto.create.CreateMemberRequest;
import allG.weato.domains.member.dto.create.CreateMemberResponse;
import allG.weato.domains.member.dto.retrieve.BookmarkResponseDto;
import allG.weato.domains.member.dto.update.UpdateProfileRequestDto;
import allG.weato.domains.member.dto.update.UpdateProfileResponseDto;
import allG.weato.domains.member.entities.AdditionalInfo;
import allG.weato.domains.member.entities.BookMark;
import allG.weato.domains.member.entities.Member;
import allG.weato.dto.error.Error404;
import allG.weato.oauth2.JwtMemberDetails;
import allG.weato.oauth2.response.ApiResponse;
import allG.weato.validation.CommonErrorCode;
import allG.weato.validation.ErrorCode;
import allG.weato.validation.RestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/members")
    public MemberResponseDto getUser() {

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);

        return new MemberResponseDto(member);
    }

    @PostMapping("/members/{memberId}")
    public CreateMemberResponse createMemberCompletely(@PathVariable("memberId") Long memberId, @RequestBody @Valid CreateMemberRequest request){
        Member member = memberService.findById(memberId);
        memberService.createMemberDetail(member,request);
        return new CreateMemberResponse(member);
    }



    @GetMapping("/members/{memberId}")// my-page
    public MemberResponseDto showMember(@PathVariable("memberId") Long memberId){

        Member member = memberService.findById(memberId);
        return new MemberResponseDto(member);

    }
    @GetMapping("/members/{memberId}/profile")//프로필
    public ProfileResponseDto showMemberProfile(@PathVariable("memberId") Long memberId){

        Member member = memberService.findById(memberId);
        if(member.getProfile()==null || member.getAdditionalInfo()==null){
            throw new RuntimeException("추가정보를 먼저 입력해주세요!");
        }
        return new ProfileResponseDto(member);
    }

    @PatchMapping("/members/{memberId}/profile")
    public UpdateProfileResponseDto updateMemberProfile(@PathVariable("memberId") Long memberId, @RequestBody @Valid UpdateProfileRequestDto request){
        Member member = memberService.findById(memberId);
        memberService.upadateProfile(member, request);
        return new UpdateProfileResponseDto(member);
    }

    @PostMapping("/members/{memberId}/additional-info")//추가정보
    public AdditionalInfoResponseDto createMemberAdditionalInfo(@PathVariable("memberId") Long memberId, @RequestBody @Valid AdditionalInfoRequestDto request){
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);
        if(member.getId()!=memberId) throw new RestException(CommonErrorCode.NOT_AUTHORIZATED);
        AdditionalInfo additionalInfo =AdditionalInfo.builder()
                .medicalHistory(request.getYears())
                .isFamilyHistory(request.getFamilyHistory())
                .isRecurrence(request.getRecurrence())
                .symptomDegree(request.getSymptomDegree())
                .build();
        additionalInfo.changeManagement(request);
        member.setAdditional_info(additionalInfo);
        memberService.save(member);

        return new AdditionalInfoResponseDto(additionalInfo);

    }

//    @GetMapping("/members/{memberId}/bookmarks") //북마크
//    public BookmarkResponseDto showBookmark(@PathVariable("memberId") Long memberId){
//        Member member = memberService.findById(memberId);
//        return new BookmarkResponseDto(member);
//    }

    @GetMapping("/members/{memberId}/bookmarks")
    public BookmarkResponseDto showBookmarkByTagType(@RequestParam(value = "tag") TagType tagType
            ,@PathVariable("memberId") Long memberId){
        Member member = memberService.findById(memberId);
        if(tagType==null) return new BookmarkResponseDto(member);
        return new BookmarkResponseDto(member,tagType);
    }

    @GetMapping("/members/{memberId}/scraped")
    public void showScrap(@PathVariable("memberId") Long memberId){
        Member member = memberService.findById(memberId);

    }




    
    
    
}
