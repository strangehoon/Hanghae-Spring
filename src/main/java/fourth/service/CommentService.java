package fourth.service;

import fourth.dto.basic.ResponseDto;
import fourth.dto.basic.ResponseMessage;
import fourth.dto.basic.StatusCode;
import fourth.entity.*;
import fourth.exception.CustomException;
import fourth.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fourth.dto.CommentDto;

import fourth.jwt.JwtUtil;

import java.util.Optional;

import static fourth.exception.Error.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final JwtUtil jwtUtil;

    // 댓글 등록
    @Transactional
    public ResponseEntity<ResponseDto> addComment(Long memoId, CommentDto.Request requestDto, Long userId) {

        User user = validUser(userId);
        Memo memo = validMemo(memoId);

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Comment comment = commentRepository.save(Comment.createComment(requestDto.getContents(), user, memo));

        return ResponseEntity.ok().body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.SUCCESS)
                .data(new CommentDto.Response(comment)).build());
    }

    // 댓글 수정
    @Transactional
    public ResponseEntity<ResponseDto> modifyComment(Long memoId, Long userId, CommentDto.Request requestDto) {

        User user = validUser(userId);

        Comment comment = validComment(memoId);

        if(!comment.getUser().equals(user))
            throw new CustomException(INVALID_AUTHORIZED);

        comment.update(requestDto.getContents());
        return ResponseEntity.ok().body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.SUCCESS)
                .data(new CommentDto.Response(comment)).build());
    }

    // 댓글 삭제
    @Transactional
    public ResponseEntity<ResponseDto> removeComment(Long commentId, Long userId) {

        User user = validUser(userId);

        Comment comment = validComment(commentId);

        if(!comment.getUser().equals(user))
            throw new CustomException(INVALID_AUTHORIZED);

        commentRepository.deleteById(comment.getId());
        return ResponseEntity.ok().body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.SUCCESS).build());
    }

    // 댓글 좋아요
    @Transactional
    public ResponseEntity<ResponseDto> likeComment(Long userId, Long commentId) {

        User user = validUser(userId);

        Comment comment = validComment(commentId);

        Optional<LikeComment> found = likeCommentRepository.findByUserAndComment(user, comment);
        if (found.isPresent()){
            likeCommentRepository.deleteById(found.get().getId());
            return ResponseEntity.ok()
                    .body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.LIKE_CANCEL).build());
        }
        else{
            LikeComment likeComment = LikeComment.createLikeComment(user, comment);
            likeCommentRepository.save(likeComment);
            return ResponseEntity.ok()
                    .body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.LIKE_SUCCESS).build());
        }
    }

    //db에 user가 존재하는지 검사
    private User validUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        return user;
    }

    //db에 memo가 존재하는지 검사
    private Memo validMemo(Long memoId) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMO)
        );
        return memo;
    }

    // db에 해당 memo의 comment가 존재하는지 검사
    private Comment validComment(Long memoId) {
        Comment comment = commentRepository.findById(memoId).orElseThrow(
                () -> new CustomException(NOT_FOUND_COMMENT)
        );
        return comment;
    }
}
