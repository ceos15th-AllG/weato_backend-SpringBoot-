package allG.weato.serviceTest;

import allG.weato.domains.newsletter.entities.Newsletter;
import allG.weato.domains.newsletter.newsletterDto.update.NewsletterUpdateRequestDto;
import allG.weato.domains.newsletter.NewsletterRepository;
import allG.weato.domains.newsletter.NewsletterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NewsletterServiceTest {
    @Autowired private NewsletterRepository newsletterRepository;
    @Autowired private NewsletterService newsletterService;


    @Test
    @DisplayName("뉴스레터 단건 조회")
    public void findOneTest(){
        //given
        Newsletter newsletter1 = new Newsletter();
        newsletter1.changeContent("this is for test");
        newsletterService.save(newsletter1);
        //when
        Long id = newsletter1.getId();
        System.out.println("newsletter.id = " + id);
        Newsletter findOne = newsletterService.findOneById(id);
        System.out.println("findOne.id = " + findOne.getId());
        //then
        assertThat(findOne.getContent()).isEqualTo(newsletter1.getContent());
    }

    @Test
    @DisplayName("뉴스레터 수정 테스트")
    public void updateTest(){
        //given
        Newsletter newsletter1 = new Newsletter();
        newsletter1.changeTitle("asdgasdasdfasg");
        newsletterService.save(newsletter1);

        //when
        NewsletterUpdateRequestDto request = new NewsletterUpdateRequestDto();
        request.setUpdatedTitle("this is for update");
        request.setUpdatedContent("updated content");
        newsletterService.updateNewsletter(newsletter1,request);

        //then
        assertThat(newsletter1.getTitle()).isEqualTo("this is for update");
        assertThat(newsletter1.getContent()).isNotEqualTo(null);

    }

//    @Test
//    @DisplayName("뉴스레터 삭제 테스트")
//    public void deleteTest(){
//        //given
//        Newsletter newsletter1 = new Newsletter();
//        newsletterService.save(newsletter1);
//
//        //when
//        Long id = newsletter1.getId();
//        newsletterService.deleteNewsletter(newsletter1);
//        //then
//        assertThat(newsletterService.findOneById(id)).isNull();
//    }



}
