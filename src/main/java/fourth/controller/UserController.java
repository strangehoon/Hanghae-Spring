package fourth.controller;

import fourth.dto.basic.ResponseDto;
import fourth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import fourth.dto.LoginDto;
import fourth.dto.SignUpDto;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> userAdd(@Validated @RequestBody SignUpDto.Request requestDto, BindingResult bindingResult) {
        // 검증 에러(여기선 단순히 로그만 출력)
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
        }
        return userService.addUser(requestDto);
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> userLogin(@Validated @RequestBody LoginDto.Request requestDto, BindingResult bindingResult) {
        // 검증 에러(여기선 단순히 로그만 출력)
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
        }
        return userService.loginUser(requestDto);
    }

}
