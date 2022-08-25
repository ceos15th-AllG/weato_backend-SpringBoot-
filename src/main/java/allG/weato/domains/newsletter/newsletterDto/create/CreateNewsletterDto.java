package allG.weato.domains.newsletter.newsletterDto.create;

import allG.weato.domains.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewsletterDto {
    String title;
    String content;
    TagType tagType;
}

