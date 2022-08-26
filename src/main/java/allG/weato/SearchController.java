package allG.weato;

import allG.weato.domains.newsletter.NewsletterService;
import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.newsletter.newsletterDto.retrieve.NewsletterDetailResponseDto;
import allG.weato.domains.post.PostService;
import allG.weato.domains.post.entities.Post;
import allG.weato.domains.post.postDto.retrieve.PostRetrieveDto;
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
        Long numOfTotalNewsletters = searchedNewsletter.getTotalElements();
        List<Newsletter> newsletters = searchedNewsletter.getContent();

        Page<Post> searchedPosts = postService.searchPostsWithKeyword(0,keyword);
        Long numOfTotalPosts = searchedPosts.getTotalElements();
        List<Post> posts = searchedPosts.getContent();

        List<NewsletterDetailResponseDto> newsletterDtos = newsletters
                .stream()
                .map(n -> new NewsletterDetailResponseDto(n)).collect(Collectors.toList());

        List<PostRetrieveDto> postRetrieveDtos = posts
                .stream()
                .map(p -> new PostRetrieveDto(p)).collect(Collectors.toList());

        return new SearchRequestDto(newsletterDtos, postRetrieveDtos,numOfTotalNewsletters,numOfTotalPosts);
    }


    @Data
    @AllArgsConstructor
    public static class SearchRequestDto<T1,T2>{
        private T1 newslettersData;
        private T2 postsData;
        private long numOfNewsletters;
        private long numOfPosts;

    }
}
