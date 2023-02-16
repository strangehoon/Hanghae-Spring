package second_hanghaememo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import second_hanghaememo.dto.basic.ApiResponseDto;
import second_hanghaememo.dto.CommentDto;
import second_hanghaememo.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/{id}")
    public ApiResponseDto saveComment(@PathVariable Long id, @RequestBody CommentDto.Request requestDto, HttpServletRequest request) {
        return commentService.save(id, requestDto, request);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ApiResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentDto.Request requestDto, HttpServletRequest request) {
        return commentService.update(id, requestDto, request);
    }

    // 특정 댓글 삭제
    @DeleteMapping("/{id}")
    public ApiResponseDto<?> deleteComment(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        return commentService.deleteOne(id, request, response);
    }


}
