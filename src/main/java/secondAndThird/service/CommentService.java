package secondAndThird.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import secondAndThird.dto.basic.ApiResponseDto;
import secondAndThird.dto.CommentDto;
import secondAndThird.dto.basic.ResponseUtils;
import secondAndThird.entity.Comment;
import secondAndThird.entity.Memo;
import secondAndThird.entity.User;
import secondAndThird.jwt.JwtUtil;
import secondAndThird.repository.CommentRepository;
import secondAndThird.repository.MemoRepository;
import secondAndThird.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;
    private final MemoRepository memoRepository;

    private final JwtUtil jwtUtil;

    @Transactional
    public ApiResponseDto<?> save(Long id, CommentDto.Request requestDto, HttpServletRequest request) {

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 댓글 작성 가능
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

            Memo memo = memoRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
            );

            // 요청받은 DTO 로 DB에 저장할 객체 만들기
            Comment comment = commentRepository.save(Comment.createComment(requestDto.getContents(), user, memo));

            return ResponseUtils.ok(new CommentDto.Response(comment, user.getUserName()));
        } else {
            return null;
        }
    }

    @Transactional
    public ApiResponseDto<?> update(Long id, CommentDto.Request requestDto, HttpServletRequest request) {

        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 댓글 수정 가능
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

            Comment comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );

            comment.update(requestDto.getContents());

            return ResponseUtils.ok(new CommentDto.Response(comment, user.getUserName()));
        } else {
            return null;
        }
    }

    @Transactional
    public ApiResponseDto<?> deleteOne(Long id, HttpServletRequest request, HttpServletResponse response) {
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
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUserName(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Comment comment = commentRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
            commentRepository.deleteById(comment.getId());

            return ResponseUtils.ok(HttpStatus.OK);
        } else {
            return null;
        }
    }

}
