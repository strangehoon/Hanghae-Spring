package second_hanghaememo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import second_hanghaememo.dto.Check;
import second_hanghaememo.dto.MemoDto;
import second_hanghaememo.dto.MemoForm;
import second_hanghaememo.service.MemoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    //home 화면
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    // 게시글 등록
    @PostMapping("/memo/new")
    public MemoDto createMemo(@RequestParam Long userId, @RequestBody MemoForm memoForm) {
        return memoService.createMemo(userId, memoForm);
    }

    // 전체게시글 조회
    @GetMapping("/memo/list")
    public List<MemoDto> getMemos(@RequestParam Long userId) {
        return memoService.getMemos(userId);
    }

    // 특정 게시글 조회
    @GetMapping("/memo/{memoId}")
    public MemoDto content(@PathVariable Long memoId, @RequestParam Long userId){
        MemoDto memoDto = memoService.findMemo(memoId, userId);
        return memoDto;
    }

    // 특정 게시글 수정
    @PutMapping("/memo/update/{memoId}")
    public MemoDto updateOne(@PathVariable Long memoId, @RequestParam Long userId, @RequestBody MemoForm memoForm) {
        return memoService.updateMemo(memoId,userId, memoForm);
    }

    // 특정 게시글 삭제
    @DeleteMapping("/memo/delete/{memoId}")
    public Check deleteOne(@PathVariable Long memoId, @RequestParam Long userId, @RequestParam String password) {
        return memoService.deleteMemo(memoId, userId, password);
    }
}
