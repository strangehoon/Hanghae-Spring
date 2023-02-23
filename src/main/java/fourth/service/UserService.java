package fourth.service;

import fourth.dto.basic.ResponseDto;
import fourth.dto.basic.ResponseMessage;
import fourth.dto.basic.StatusCode;
import fourth.exception.CustomException;
import fourth.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fourth.dto.*;
import fourth.entity.User;
import fourth.entity.UserRoleEnum;
import fourth.jwt.JwtUtil;
import fourth.repository.UserRepository;

import java.util.Optional;

import static fourth.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원 가입
    @Transactional
    public ResponseEntity<ResponseDto> addUser(SignUpDto.Request requestDto){
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();
        System.out.println("UserService.addUser");

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUserName(userName);
        if (found.isPresent())
            throw new CustomException(DUPLICATED_USER);

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomException(WRONG_TOKEN);
            }
            role = UserRoleEnum.ADMIN;
        }

        // 회원 저장
        User user = User.createUser(userName, password, role);
        userRepository.save(user);

        return ResponseEntity.ok()
                .body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.SUCCESS).build());
    }

    // 로그인
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> loginUser(LoginDto.Request requestDto) {
        String userName = requestDto.getUserName();
        String password = requestDto.getPassword();

        // 사용자 확인
        Optional<User> found = userRepository.findByUserName(userName);
        if (!found.isPresent())
            throw new CustomException(NOT_FOUND_USER);

        // 비밀번호 확인
        if(!found.get().getPassword().equals(password))
            throw new CustomException(INVALID_PASSWORD);

        return ResponseEntity.ok().header(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(found.get().getUserName(), found.get().getRole()))
                .body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.SUCCESS).build());
    }
}
