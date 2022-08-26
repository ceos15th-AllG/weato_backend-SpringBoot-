package allG.weato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultForCommunity<T> {
    private T hotTopics;
    private T question;
    private T management;
}
