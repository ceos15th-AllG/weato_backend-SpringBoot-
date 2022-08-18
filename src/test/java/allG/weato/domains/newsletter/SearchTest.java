package allG.weato.domains.newsletter;

import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.post.PostRepository;
import allG.weato.domains.post.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchTest {

    @Autowired private NewsletterService newsletterService;
    @Autowired private NewsletterRepository newsletterRepository;
    @Autowired private PostService postService;
    @Autowired private PostRepository postRepository;

    @Test
    @DisplayName("pageRequest 객체 관련 작업 가능성")
    public void addTwoPageRequest(){

    }

}