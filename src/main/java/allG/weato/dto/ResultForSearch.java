package allG.weato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultForSearch<T> {
    private T data;
    private int min;
    private int max;
    private int current;
    private long totalNum;
}
