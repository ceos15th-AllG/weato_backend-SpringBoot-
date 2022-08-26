package allG.weato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultForPaging<T> {
    private T data;
    private int min;
    private int max;
    private int current;
}
