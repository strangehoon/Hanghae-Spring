package fourth.controller;

import fourth.dto.basic.ResponseDto;
import fourth.entity.UserRoleEnum;
import fourth.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import fourth.dto.CommentDto;
import fourth.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/{id}")
    public ResponseEntity<ResponseDto> commentAdd(@PathVariable Long id, @RequestBody CommentDto.Request requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.addComment(id, requestDto, userDetails.getUser().getId());
    }

    // 댓글 수정
    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> commentModify(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @RequestBody CommentDto.Request requestDto) {
        return commentService.modifyComment(id, userDetails.getUser().getId(), requestDto);
    }

    // 댓글 삭제
    @Secured(value = UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> commentRemove(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.removeComment(id, userDetails.getUser().getId());
    }

    // 댓글 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<ResponseDto> commentLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.likeComment(userDetails.getUser().getId(), id);
    }


}
