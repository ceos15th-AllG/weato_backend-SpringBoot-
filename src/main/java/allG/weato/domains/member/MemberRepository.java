package allG.weato.domains.member;

import allG.weato.domains.member.entities.Member;
import allG.weato.domains.enums.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { //타입 ,pk타입.
    List<Member> findByName(String name);

    Optional<Member> findByEmail(String Email);
    Member findMemberByEmail(String Email);

    Member findByUserId(String userId);

    Optional<Member> findByNickname(String nickname);

//    @Query(name = "select m from Member m")
//    List<Member> findAllByEmail();



}
