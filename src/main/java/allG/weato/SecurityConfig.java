package allG.weato;

import allG.weato.domains.member.MemberRepository;
import allG.weato.oauth2.Filter.JwtRequestFilter;
import allG.weato.oauth2.JwtAuthenticationEntryPoint;
import allG.weato.oauth2.handlers.OAuth2AuthenticationFailureHandler;
import allG.weato.oauth2.handlers.OAuth2AuthenticationSuccessHandler;
import allG.weato.oauth2.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import allG.weato.oauth2.service.CustomOAuth2MemberService;
import allG.weato.oauth2.service.JwtMemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberRepository memberRepository;
    private final CustomOAuth2MemberService memberService;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandler;
    private final HttpCookieOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;


    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtRequestFilter jwtRequestFilter;


    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtMemberDetailsService memberDetailsService(){
        return new JwtMemberDetailsService(memberRepository);
    }





    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.cors()
                    .and()
                .csrf()
                    .disable()
                .formLogin()
                    .disable()
                .authorizeRequests().antMatchers("/","/swagger-ui/index.html/**").permitAll()
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
                    .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .baseUri("/oauth2/authorization")
                    .and()
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/naver")
                    .and()
                .userInfoEndpoint()
                .userService(memberService)
                    .and()
                .successHandler(successHandler)
                .failureHandler(failureHandler);


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return(web -> web.ignoring().antMatchers("/profileUploads/**","/messageUploads/**", "/js/**","/webjars/**"));
    }

}
