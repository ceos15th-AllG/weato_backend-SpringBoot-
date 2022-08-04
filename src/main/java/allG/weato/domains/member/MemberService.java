package allG.weato.domains.member;

import allG.weato.domains.comment.entities.Comment;
import allG.weato.domains.enums.TagType;
import allG.weato.domains.member.dto.create.CreateMemberRequest;
import allG.weato.domains.member.dto.update.UpdateProfileRequestDto;
import allG.weato.domains.member.entities.BookMark;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.newsletter.entities.Newsletter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    public Member findById(Long id){
      return memberRepository.findById(id).get();
    }

    public Member findByEmail(String email){
      Optional<Member> member  = memberRepository.findByEmail(email);
      Member findMember = member.get();
      return findMember;

    }

    @Transactional
    public void save(Member member){
        memberRepository.save(member);
    }

    public Member getMember(String userId){
        return memberRepository.findByUserId(userId);
    }

    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validateDuplicateMember(Member member){
        List<Member> findMembersByName = memberRepository.findByName(member.getName());
//        List<Member> findMembersByEmail = memberRepository.findAllByEmail();
        if (findMembersByName.size()!=0){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    @Transactional
    public void addComment(Member member, Comment comment) {
        member.addComment(comment);
    }

    @Transactional
    public void createMemberDetail(Member member, CreateMemberRequest request) {
        member.changeNickname(request.getNickname());
        member.getProfile().changeImgurl(request.getImageUrl());
        member.changeNewsletterEmail(request.getNewsletterEmail());
        if(request.getDrug()) member.getTagTypeList().add(TagType.DRUG);
        if(request.getCleaning()) member.getTagTypeList().add(TagType.CLEANING);
        if(request.getFood()) member.getTagTypeList().add(TagType.FOOD);
        if(request.getEnvironment()) member.getTagTypeList().add(TagType.ENVIRONMENT);
        if(request.getSleep()) member.getTagTypeList().add(TagType.SLEEP);
        if(request.getEtc()) member.getTagTypeList().add(TagType.OTHERWISE);
        memberRepository.save(member);
    }

    @Transactional
    public void upadateProfile(Member member, UpdateProfileRequestDto request) {
        member.getProfile().changeImgurl(request.getImageUrl());
        member.changeNickname(request.getNickname());
        member.getAdditionalInfo().setMedicalHistory(request.getMedicalHistory());
        member.getAdditionalInfo().setIsFamilyHistory(request.getIsFamilyHistory());
        member.getAdditionalInfo().setIsRecurrence(request.getIsRecurrence());
        member.getAdditionalInfo().changeManagement(request);
        member.changeTagTypesByUpdate(request);
        memberRepository.save(member);
    }


    @Transactional
    public void emailValidation(Member member) {
        member.changeEmailValidation();
        int newNum =  (int)(Math.random() * (99999 - 10000 + 1)) + 10000;
        member.changeAuthNum(newNum);
    }

    @Transactional
    public void emailValidation(Member member, int num) {
        member.changeAuthNum(num);
    }
}
