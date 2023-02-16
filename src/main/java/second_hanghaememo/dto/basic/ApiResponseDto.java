package second_hanghaememo.dto.basic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class ApiResponseDto<T> {
    private boolean success;
    private T response;
    private ErrorResponse error;

    public ApiResponseDto(boolean success, T response) {
        this.success = success;
        this.response = response;
    }
    public ApiResponseDto(boolean success, T response, ErrorResponse error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }
}
