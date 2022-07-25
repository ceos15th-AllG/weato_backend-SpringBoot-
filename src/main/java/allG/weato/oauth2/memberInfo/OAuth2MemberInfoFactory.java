package allG.weato.oauth2.memberInfo;

import allG.weato.domains.enums.ProviderType;

import java.util.Map;

public class OAuth2MemberInfoFactory {

    public static OAuth2MemberInfo getOAuth2MemberInfo(ProviderType providerType, Map<String,Object> attributes){
        switch (providerType) {
//            case GOOGLE: return new GoogleOAuth2MemberInfo(attributes);
//            case FACEBOOK: return new FacebookOAuth2MemberInfo(attributes);
            case NAVER: return new NaverOAuth2MemberInfo(attributes);
//            case KAKAO: return new KakaoOAuth2MemberInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
