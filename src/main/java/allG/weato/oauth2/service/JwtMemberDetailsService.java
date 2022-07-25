package allG.weato.oauth2.service;

import allG.weato.domains.member.MemberRepository;
import allG.weato.domains.member.entities.Member;
import allG.weato.oauth2.JwtMemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtMemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member entityMember = memberRepository.findByEmail(username).get();

        if(entityMember != null) return new JwtMemberDetails(entityMember);

        throw new UsernameNotFoundException("Could not find user with email : " + username);
    }
}
