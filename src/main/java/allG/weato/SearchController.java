package allG.weato;

import allG.weato.domains.newsletter.NewsletterRepository;
import allG.weato.domains.newsletter.NewsletterService;
import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.newsletter.newsletterDto.retrieve.NewsletterRetrieveDto;
import allG.weato.domains.post.PostService;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.post.postDto.retrieve.PostDto;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchController {

    private final PostService postService;
    private final NewsletterService newsletterService;

    @GetMapping("/search")
    public SearchRequestDto searchPostsAndNewsletters(
            @RequestParam(value = "keyword") String keyword){
        Page<Newsletter> searchedNewsletter = newsletterService.searchNewslettersWithKeyword(0,keyword);
        List<Newsletter> newsletters = searchedNewsletter.getContent();

        Page<Post> searchedPosts = postService.searchPostsWithKeyword(0,keyword);
        List<Post> posts = searchedPosts.getContent();

        List<NewsletterRetrieveDto> newsletterDtos = newsletters
                .stream()
                .map(n -> new NewsletterRetrieveDto(n)).collect(Collectors.toList());

        List<PostDto> postDtos = posts
                .stream()
                .map(p -> new PostDto(p)).collect(Collectors.toList());

        return new SearchRequestDto(newsletterDtos,postDtos);
    }


    @Data
    @AllArgsConstructor
    public static class SearchRequestDto<T1,T2>{
        private T1 newslettersData;
        private T2 postsData;
    }
}
