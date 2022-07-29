package allG.weato.domains.post;

import allG.weato.domains.post.entities.Post;
import allG.weato.domains.enums.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {
      Post findPostById(Long id);
      Post findPostByTitle(String title);

      Page<Post> findAll(Pageable pageable);
      Page<Post> findPostsByBoardType(Pageable pageable, BoardType boardType);

      @Modifying
      @Transactional
      @Query("delete from PostLike pl where pl.id = :postLikeId")
      void deletePostLikeById(@Param("postLikeId") Long postLikeId);

      @Modifying
      @Transactional
      @Query("delete from Scrap s where s.id = :scrapId")
      void deleteScrapById(@Param("scrapId")Long scrapId);

//    @Query("delete from Comment c where c.id = :commentId")
//    void deleteCommentById(@Param("commentId") Long commentId);



}
