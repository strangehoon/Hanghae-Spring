package fourth.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionDto {
    private int status;
    private String message;

    private String code;

}
