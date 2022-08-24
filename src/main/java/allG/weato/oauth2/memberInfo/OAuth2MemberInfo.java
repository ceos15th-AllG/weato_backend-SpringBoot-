package allG.weato.oauth2.memberInfo;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public abstract class OAuth2MemberInfo {

    protected Map<String, Object> attributes;

    public OAuth2MemberInfo(Map<String,Object> attributes){
        this.attributes=attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getGender();

    public abstract String getBirthyear();

    public abstract String getBirthday();
}
