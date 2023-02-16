package second_hanghaememo.dto.basic;

import org.springframework.http.HttpStatus;

public class ResponseUtils {

    public static <T> ApiResponseDto<T> ok(T data) {
        return new ApiResponseDto<>(true, data, null);
    }

    public static <T> ApiResponseDto<T> error(HttpStatus status, String message) {
        return new ApiResponseDto<>(false, null, new ErrorResponse(status, message));
    }
}





