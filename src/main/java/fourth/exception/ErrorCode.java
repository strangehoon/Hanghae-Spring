package fourth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 400 BAD_REQUEST 잘못된 요청
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "400", "비밀번호 오류"),
    WRONG_TOKEN(HttpStatus.BAD_REQUEST, "400", "토큰 오류"),

    // 401 UNAUTHORIZED 비인증
    INVALID_AUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "사용자 비인증"),

    // 404 NOT_FOUND 잘못된 리소스 접근
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "404", "존재하지 않는 회원"),
    NOT_FOUND_MEMO(HttpStatus.NOT_FOUND, "404", "존재하지 않는 메모"),

    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "404", "존재하지 않는 댓글"),


    // 409 CONFLICT 중복된 리소스
    DUPLICATED_USER(HttpStatus.CONFLICT, "409", "회원 중복 에러");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

}

