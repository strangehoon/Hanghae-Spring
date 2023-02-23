package secondAndThird.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import secondAndThird.dto.*;
import secondAndThird.dto.basic.ApiResponseDto;
import secondAndThird.dto.basic.ResponseUtils;
import secondAndThird.entity.User;
import secondAndThird.entity.UserRoleEnum;
import secondAndThird.jwt.JwtUtil;
import secondAndThird.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원 가입
    @Transactional
    public ApiResponseDto<?> createUser(SignUpDto.Request requestDto, HttpServletResponse response){
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUserName(userName);
        if (found.isPresent()) {
            return ResponseUtils.error(HttpStatus.BAD_REQUEST,"중복된 회원입니다.");
        }
        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                return ResponseUtils.error(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = User.createUser(userName, password, role);
        userRepository.save(user);
        return ResponseUtils.ok(HttpStatus.OK);
    }

    // 로그인
    @Transactional(readOnly = true)
    public ApiResponseDto<?> login(LoginDto.Request requestDto, HttpServletResponse response) {
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();

        // 사용자 확인
        Optional<User> found = userRepository.findByUserName(userName);
        if (!found.isPresent()) {
            return ResponseUtils.error(HttpStatus.BAD_REQUEST,"회원을 찾을 수 없습니다.");
        }
        // 비밀번호 확인
        if(!found.get().getPassword().equals(password)){
            return ResponseUtils.error(HttpStatus.BAD_REQUEST,"회원을 찾을 수 없습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(found.get().getUserName(), "user"));
        return ResponseUtils.ok(HttpStatus.OK);
    }
}
