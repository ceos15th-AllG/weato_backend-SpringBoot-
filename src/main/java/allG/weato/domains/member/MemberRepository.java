package allG.weato.domains.member;

import allG.weato.domains.member.entities.Member;
import allG.weato.domains.enums.ProviderType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { //타입 ,pk타입.
    List<Member> findByName(String name);

    Optional<Member> findByEmail(String Email);
    Member findMemberByEmail(String Email);

    @EntityGraph(attributePaths = {"profile","additional-info","bookMarkList"})
    Member findMemberById(Long id);


    Member findByUserId(String userId);

    Member findMemberByNickname(String nickname);

//    @Query(name = "select m from Member m")
//    List<Member> findAllByEmail();



}
