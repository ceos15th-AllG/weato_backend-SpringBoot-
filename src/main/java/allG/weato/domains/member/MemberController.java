package allG.weato.domains.member;


import allG.weato.domains.enums.BoardType;
import allG.weato.domains.enums.TagType;
import allG.weato.domains.member.MemberService;
import allG.weato.domains.member.dto.*;
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
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Current member", description = "현재 로그인된 멤버")
    @GetMapping("/members")
    public MemberResponseDto getUser() {

        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);

        return new MemberResponseDto(member);
    }

    @Operation(summary = "Create member profile(profile)", description = "멤버 프로필 생성")
    @PostMapping("/members/{memberId}")
    public CreateMemberResponse createMemberCompletely(@PathVariable("memberId") Long memberId, @RequestBody @Valid CreateMemberRequest request){
        Member member = memberService.findById(memberId);
        memberService.createMemberDetail(member,request);
        return new CreateMemberResponse(member);
    }

    @GetMapping("/members/validation")
    public boolean validationCheckForNickname(@RequestParam("nickname") String nickname){
        return memberService.validateAboutNickname(nickname);
    }

    @Operation(summary = "Retrieve specific member", description = "특정 멤버 조회")
    @GetMapping("/members/{memberId}")// my-page
    public MemberResponseDto showMember(@PathVariable("memberId") Long memberId){

        Member member = memberService.findById(memberId);
        return new MemberResponseDto(member);

    }
    @Operation(summary = "Retrieve profile of specific member", description = "특정 멤버 프로필 조회")
    @GetMapping("/members/{memberId}/profile")//프로필
    public ProfileResponseDto showMemberProfile(@PathVariable("memberId") Long memberId){

        Member member = memberService.findMemberForProfile(memberId);
        if(member.getProfile().getId()==null || member.getAdditionalInfo()==null){
            throw new RuntimeException("추가정보를 먼저 입력해주세요!");
        }
        return new ProfileResponseDto(member);
    }

    @Operation(summary = "Update member profile", description = "멤버 프로필 수정")
    @PatchMapping("/members/{memberId}/profile")
    public UpdateProfileResponseDto updateMemberProfile(@PathVariable("memberId") Long memberId, @RequestBody @Valid UpdateProfileRequestDto request){
        Member member = memberService.findById(memberId);
        memberService.upadateProfile(member, request);
        return new UpdateProfileResponseDto(member);
    }

    @Operation(summary = "Create Additional-info of member", description = "멤버 추가정보 생성")
    @PostMapping("/members/{memberId}/additional-info")//추가정보
    public AdditionalInfoResponseDto createMemberAdditionalInfo(@PathVariable("memberId") Long memberId, @RequestBody @Valid AdditionalInfoRequestDto request){
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email =  principal.getUsername();
        Member member = memberService.findByEmail(email);
        if(member.getId()!=memberId) throw new RestException(CommonErrorCode.NOT_AUTHORIZATED);
//        AdditionalInfo additionalInfo =AdditionalInfo.builder()
//                .medicalHistory(request.getYears())
//                .isFamilyHistory(request.getFamilyHistory())
//                .isRecurrence(request.getRecurrence())
//                .symptomDegree(request.getSymptomDegree())
//                .build();

        AdditionalInfo additionalInfo = new AdditionalInfo(request.getYears(), request.getRecurrence(),request.getFamilyHistory(),request.getSymptomDegree());
        additionalInfo.changeManagement(request);
        member.setAdditional_info(additionalInfo);
        memberService.save(member);

        return new AdditionalInfoResponseDto(additionalInfo);

    }


    @Operation(summary = "Retrieve post which is bookmarked", description = "북마크된 게시글 조회")
    @GetMapping("/members/{memberId}/bookmarks")
    public BookmarkResponseDto showBookmarkByTagType(@RequestParam(value = "tag",defaultValue = "all") String code
            ,@PathVariable("memberId") Long memberId){
        code=code.toUpperCase();
        TagType tagType = TagType.valueOf(code);
        Member member = memberService.findMemberForProfile(memberId);
        if(tagType==tagType.ALL) return new BookmarkResponseDto(member);
        else return new BookmarkResponseDto(member,tagType);
    }

    @GetMapping("/members/{memberId}/scraped")
    public MemberScrapedPostDto showScrap(@PathVariable("memberId") Long memberId
            ,@RequestParam(value = "type",defaultValue = "all")String code){

        code=code.toUpperCase();
        BoardType boardType = BoardType.valueOf(code);
        Member member = memberService.findById(memberId);
        if(boardType==BoardType.ALL) return new MemberScrapedPostDto(member);
        else return new MemberScrapedPostDto(member,boardType);

    }
}
