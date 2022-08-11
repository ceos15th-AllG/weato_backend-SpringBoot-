package allG.weato;

import allG.weato.domains.newsletter.NewsletterRepository;
import allG.weato.domains.newsletter.NewsletterService;
import allG.weato.domains.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchController {
    private final PostService postService;
    private final NewsletterService newsletterService;

//    @GetMapping("/search")
//    public SearchRequestDto searchPostsAndNewsletters(@RequestParam("keyword") String keyword,@RequestParam(value = "page",defaultValue = "1") Integer page){
//
//    }


    public static class SearchRequestDto<T1,T2>{
        private T1 newslettersData;
        private T2 postsData;
        private int newsletterMin;
        private int newsletterMax;
        private int newsletterCurrent;
        private int postMin;
        private int postMax;
        private int postCurrent;
    }
}
