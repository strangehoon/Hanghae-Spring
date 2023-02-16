package second_hanghaememo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request {

        @NotBlank
        @Pattern(regexp = "[a-z|0-9]{4,10}")
        private String userName;

        @NotBlank
        @Pattern(regexp = "[a-zA-Z|0-9|~!@#$%^&*()_+|<>?:{}]{8,15}")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {

        private String message;
        private int status;

        public Response(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }
}
