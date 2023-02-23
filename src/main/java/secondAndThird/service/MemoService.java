package secondAndThird.service;


import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import secondAndThird.dto.basic.ApiResponseDto;
import secondAndThird.dto.MemoDto;
import secondAndThird.dto.basic.ResponseUtils;
import secondAndThird.entity.Memo;
import secondAndThird.entity.User;
import secondAndThird.entity.UserRoleEnum;
import secondAndThird.jwt.JwtUtil;
import secondAndThird.repository.MemoRepository;
import secondAndThird.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Transactional
    public ApiResponseDto<?> save(MemoDto.Request requestDto, HttpServletRequest request) {

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 작성 가능
        if (token != null) {

            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return ResponseUtils.error(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUserName(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Memo memo = memoRepository.save(Memo.createMemo(requestDto.getTitle(), requestDto.getContents(), user));
            return ResponseUtils.ok(new MemoDto.Response(memo));
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public ApiResponseDto<?> getMemoList() {
        List<MemoDto.Response> memoResponseDtoList = new ArrayList<>();
        List<Memo> memoList = memoRepository.findAllByOrderByModifiedAtDesc();
        for(Memo memo : memoList)
            memoResponseDtoList.add(new MemoDto.Response(memo));
        return ResponseUtils.ok(memoResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ApiResponseDto<?> findMemo(Long memoId) {
         Memo memo = memoRepository.findById(memoId).orElseThrow(
                 () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
         );
         return ResponseUtils.ok(new MemoDto.Response(memo));
    }

    @Transactional
    public ApiResponseDto<?> updateOne(Long memoId, MemoDto.UpdateRequest requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 수정 가능
        if (token != null) {

            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return ResponseUtils.error(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUserName(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 권한이 USER일 경우
            if(user.getRole().equals(UserRoleEnum.USER)){
                Optional<Memo> memo = memoRepository.findByIdAndUserId(memoId, user.getId());
                if(!memo.isPresent())
                    return ResponseUtils.error(HttpStatus.BAD_REQUEST,"작성자만 수정할 수 있습니다.");
                memo.get().update(requestDto);
            }
            // 권한이 ADMIN일 경우
            else if (user.getRole().equals(UserRoleEnum.ADMIN)){
                Memo memo = memoRepository.findById(memoId).orElseThrow(
                        () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
                );
                memo.update(requestDto);
            }

            return ResponseUtils.ok(HttpStatus.OK);
        } else {
            return null;
        }
    }
    @Transactional
    public ApiResponseDto<?> deleteOne(Long memoId, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("memoId = " + memoId);
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 게시글 삭제 가능
        if (token != null) {

            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return ResponseUtils.error(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUserName(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            // 권한이 USER일 경우
            if(user.getRole().equals(UserRoleEnum.USER)){
                Optional<Memo> memo = memoRepository.findByIdAndUserId(memoId, user.getId());
                if(!memo.isPresent())
                    return ResponseUtils.error(HttpStatus.BAD_REQUEST,"작성자만 삭제할 수 있습니다.");
                memoRepository.deleteById(memoId);
            }
            // 권한이 ADMIN일 경우
            else if (user.getRole().equals(UserRoleEnum.ADMIN)){
                Memo memo = memoRepository.findById(memoId).orElseThrow(
                        () -> new NullPointerException("해당 게시물은 존재하지 않습니다.")
                );
                memoRepository.deleteById(memoId);
            }

            return ResponseUtils.ok(HttpStatus.OK);
        } else {
            return null;
        }
    }
}