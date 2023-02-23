package fourth.dto.basic;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto<T> {

    private int status;
    private String message;
    private T data;

}