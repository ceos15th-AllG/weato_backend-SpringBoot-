package allG.weato.oauth2.memberInfo;

import java.util.Map;

public class NaverOAuth2MemberInfo extends OAuth2MemberInfo {

    public NaverOAuth2MemberInfo(Map<String, Object> attributes) {
        super(attributes);
    }


    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        System.out.println(response);

        return (String) response.get("id");
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("name");
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("email");
    }

    @Override
    public String getImageUrl() {
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");

        if(response==null){
            return null;
        }

        return (String) response.get("imageUrl");
    }

    @Override
    public String getFirstName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        String name = (String)response.get("name");

        return name.substring(1);
    }

    @Override
    public String getLastName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        String name = (String)response.get("name");

        return name.substring(0,1);
    }

    @Override
    public String getGender() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("gender");
    }

    @Override
    public String getBirthyear() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("birthyear");
    }

    @Override
    public String getBirthday(){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("birthday");
    }
}
