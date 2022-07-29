package allG.weato.domains.newsletter;

import allG.weato.domains.member.entities.BookMark;
import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.enums.TagType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface NewsletterRepository  extends JpaRepository<Newsletter,Long> {


    Page<Newsletter> findNewslettersByTagType(TagType tagType, Pageable pageable);

    Page<Newsletter> findAll(Pageable pageable);


    @Modifying
    @Transactional
    @Query("delete from BookMark bm where bm.id = :bookMarkId")
    void deleteBookMark(@Param("bookMarkId")Long bookMarkId);

    @Modifying
    @Transactional
    @Query("delete from NewsletterLike nl where nl.id = :newsletterLikeId")
    void deleteNewsletterLike(@Param("newsletterLikeId")Long newsletterLikeId);
}
