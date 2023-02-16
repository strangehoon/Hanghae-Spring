package second_hanghaememo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import second_hanghaememo.dto.basic.ApiResponseDto;
import second_hanghaememo.dto.MemoDto;
import second_hanghaememo.service.MemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {

    private final MemoService memoService;

    // 게시글 등록
    @PostMapping("/")
    public ApiResponseDto<?> saveMemo(@RequestBody MemoDto.Request requestDto, HttpServletRequest request) {
        return memoService.save(requestDto, request);
    }

    // 전체게시글 조회
    @GetMapping("/list")
    public ApiResponseDto<?> getMemos() {
        return memoService.getMemoList();
    }

    // 특정 게시글 조회
    @GetMapping("/{id}")
    public ApiResponseDto<?> content(@PathVariable Long id){
        return memoService.findMemo(id);
    }

    // 특정 게시글 수정
    @PutMapping("/{id}")
    public ApiResponseDto<?> updateMemo(@PathVariable Long id, @RequestBody MemoDto.UpdateRequest requestDto, HttpServletRequest request) {
        return memoService.updateOne(id, requestDto, request);
    }

    // 특정 게시글 삭제
    @DeleteMapping("/{id}")
    public ApiResponseDto<?> deleteMemo(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        return memoService.deleteOne(id, request, response);
    }
}
