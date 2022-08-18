package allG.weato.domains.post;

import allG.weato.domains.comment.entities.Comment;
import allG.weato.domains.member.entities.Member;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.post.entities.PostLike;
import allG.weato.domains.enums.BoardType;
import allG.weato.domains.post.entities.Scrap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void addComment(Post post, Comment comment) {
        post.addComment(comment);
    }

    @Transactional
    public void save(Post post) {
        postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findPostById(Long id) {
        return postRepository.findPostById(id);
    }

    public Post findPostFetchById(Long id){
        return postRepository.findPostFecthJoin(id);
    }

    public Post findPostByTitle(String title) {
        return postRepository.findPostByTitle(title);
    }

    public Page<Post> findPostWithPaging(Integer page){
        PageRequest pageRequest = PageRequest.of(page,6, Sort.by(Sort.Direction.DESC,"createdAt"));
        return postRepository.findAll(pageRequest);
    }

    public Page<Post> findPostPageWithBoardType(Integer page, BoardType boardType){
        PageRequest pageRequest = PageRequest.of(page,6,Sort.by(Sort.Direction.DESC,"createdAt"));
        return postRepository.findPostsByBoardType(pageRequest,boardType);
    }

    public Page<Post> searchPostsWithKeyword(Integer page, String keyword){
        PageRequest pageRequest = PageRequest.of(page,6,Sort.by(Sort.Direction.DESC,"createdAt"));
        return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword,keyword,pageRequest);
    }

    @Transactional
    public void updatePostContent(Post post, String content) {
        post.changeContent(content);
    }

    @Transactional
    public void updatePostTitle(Post post, String title) {
        post.changeTitle(title);
    }

//    @Transactional
//    public void addViews(Post post) {
//        post.addViews();
//    }

    @Transactional
    public void updatePost(Post post, String title, String content) {

        post.changeTitle(title);
        post.changeContent(content);
    }

    @Transactional
    public void deletePost(Post post) {
        Member member = post.getMember();
        member.deletePost(post);
        postRepository.delete(post);
    }

    @Transactional
    public void addLike(Member member, Post post, PostLike postLike) {
        postLike.setOwner(member);
        post.addLike(postLike);
    }

    @Transactional
    public void deleteLike(Member member, Post post, PostLike postLike){
        member.deletePostLike(postLike);
        post.deleteLike(postLike);
        postRepository.deletePostLikeById(postLike.getId());
    }

    @Transactional
    public void scrapPost(Member member, Post post, Scrap scrap) {
        member.addScrap(scrap);
        post.addScrap(scrap);
    }

    @Transactional
    public void deleteScrap(Member member, Post post, Scrap scrap){
        member.deleteScrap(scrap);
        post.deleteScrap(scrap);
        postRepository.deleteScrapById(scrap.getId());
    }
}

