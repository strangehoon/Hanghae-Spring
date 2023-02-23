package secondAndThird.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import secondAndThird.dto.basic.ApiResponseDto;
import secondAndThird.dto.LoginDto;
import secondAndThird.dto.SignUpDto;
import secondAndThird.service.UserService;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ApiResponseDto<?> signUp(@Validated @RequestBody SignUpDto.Request requestDto, BindingResult bindingResult,
                                 HttpServletResponse response){
        // 검증 에러
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
        }
        return userService.createUser(requestDto, response);
    }


    @PostMapping("/login")
    public ApiResponseDto<?> login(@Validated @RequestBody LoginDto.Request requestDto, BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
        }
        return userService.login(requestDto, response);
    }


}
