package allG.weato.oauth2.service;

import allG.weato.domains.enums.ProviderType;
import allG.weato.domains.enums.Role;
import allG.weato.domains.member.MemberRepository;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.member.entities.Profile;
import allG.weato.oauth2.JwtMemberDetails;
import allG.weato.oauth2.exception.OAuthProviderMissMatchException;
import allG.weato.oauth2.memberInfo.OAuth2MemberInfo;
import allG.weato.oauth2.memberInfo.OAuth2MemberInfoFactory;
import allG.weato.oauth2.properties.AppProperties;
import allG.weato.oauth2.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("attributes" + super.loadUser(userRequest).getAttributes());
        OAuth2User user = super.loadUser(userRequest);

        try{
            return process(userRequest,user);
        } catch (AuthenticationException ex){
            throw new OAuth2AuthenticationException(ex.getMessage());
        } catch (Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }


    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        //provider타입에 따라서 각각 다르게 userInfo가져온다. (가져온 필요한 정보는 OAuth2UserInfo로 동일하다)
        OAuth2MemberInfo memberInfo = OAuth2MemberInfoFactory.getOAuth2MemberInfo(providerType, user.getAttributes());
        System.out.println("memberInfo.name = " +memberInfo.getName()+", memberInfo.getEmail() = " + memberInfo.getEmail());
        Member savedMember = memberRepository.findMemberByEmail(memberInfo.getEmail());

        if (savedMember!= null)
        {
            if (providerType != savedMember.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + savedMember.getProviderType() + " account to login."
                );
            }
            updateMember(savedMember, memberInfo);
        } else {
            savedMember = createMember(memberInfo, providerType);
        }
        System.out.println("token = "+ jwtTokenUtil.generateToken(memberInfo.getEmail()));

        return new JwtMemberDetails(savedMember, user.getAttributes());
    }


    private Member createMember(OAuth2MemberInfo memberInfo, ProviderType providerType){

        Member member = new Member(
                memberInfo.getName(), memberInfo.getEmail(), memberInfo.getGender(), memberInfo.getBirthyear(),memberInfo.getBirthday(), Role.USER,providerType
        );
        Profile profile = new Profile();
        profile.changeImgurl(memberInfo.getImageUrl());
        member.addProfile(profile);
        return memberRepository.save(member);
    }


    private Member updateMember(Member member, OAuth2MemberInfo memberInfo){
        if (memberInfo.getName() != null && !member.getName().equals(memberInfo.getName())) {
            member.changeName(memberInfo.getName());
        }

        return member;
    }
}
