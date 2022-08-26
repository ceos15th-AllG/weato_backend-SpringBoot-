package allG.weato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultForList <T>{
    private T data;
}
