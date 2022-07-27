package allG.weato.oauth2.controller;

import allG.weato.domains.enums.Role;
import allG.weato.domains.member.MemberRefreshTokenRepository;
import allG.weato.domains.member.entities.MemberRefreshToken;
import allG.weato.oauth2.JwtMemberDetails;
import allG.weato.oauth2.properties.AppProperties;
import allG.weato.oauth2.request.JwtRequest;
import allG.weato.oauth2.response.ApiResponse;
import allG.weato.oauth2.service.JwtMemberDetailsService;
import allG.weato.oauth2.utils.CookieUtils;
import allG.weato.oauth2.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;

@RestController
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final static long THREE_DAYS_MSEC = 259200000;

    private final AppProperties appProperties;
    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    private final JwtMemberDetailsService memberDetailsService;

    private final MemberRefreshTokenRepository memberRefreshTokenRepository;


    @GetMapping("/refresh")
    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response){
        JwtMemberDetails principal = (JwtMemberDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String accessToken = request.getHeader("Authorization").substring(7);
        if(!jwtTokenUtil.validateToken(accessToken,principal)){
            return ApiResponse.invalidAccessToken();
        }

        Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(accessToken);
        Date now = new Date();
        if(now.before(expirationDate)){
            return ApiResponse.notExpiredTokenYet();
        }

        String email = jwtTokenUtil.getClaimFromToken(accessToken,Claims::getSubject);
        Role role = Role.USER;
        Cookie[] cookies = request.getCookies();
        String refreshToken = CookieUtils.getCookie(request,REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));


        if(!jwtTokenUtil.validateToken(refreshToken,principal)){
            return ApiResponse.invalidRefreshToken();
        }

        MemberRefreshToken memberRefreshToken = memberRefreshTokenRepository.findByUserIdAndRefreshToken(email,refreshToken);
        if(memberRefreshToken==null){
            return ApiResponse.invalidRefreshToken();
        }

        String newAccessToken= jwtTokenUtil.generateToken(email);
        long validTime = jwtTokenUtil.getExpirationDateFromToken(refreshToken).getTime()- now.getTime();

        if(validTime<THREE_DAYS_MSEC){
            long refreshTokenExpiry=appProperties.getAuth().getTokenExpiry();

            refreshToken=jwtTokenUtil.generateToken(email);

            memberRefreshToken.setRefreshToken(refreshToken);

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtils.addCookie(response, REFRESH_TOKEN, refreshToken, cookieMaxAge);

        }

        return ApiResponse.success("token",refreshToken);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(authentication.getAuthorities().toString());

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
