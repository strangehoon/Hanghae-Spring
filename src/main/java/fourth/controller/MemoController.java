package fourth.controller;


import fourth.dto.MemoDto;
import fourth.dto.basic.ResponseDto;
import fourth.entity.UserRoleEnum;
import fourth.security.UserDetailsImpl;
import fourth.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {

    private final MemoService memoService;

    // 게시글 등록
    @PostMapping
    public ResponseEntity<ResponseDto> memoAdd(@RequestBody MemoDto.Request requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.addMemo(requestDto, userDetails.getUser());
    }

    // 전체게시글 조회
    @GetMapping
    public ResponseEntity<ResponseDto> memoList() {
        return memoService.findMemos();
    }

    // 특정 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> memoDetails(@PathVariable Long id){
        return memoService.findMemo(id);
    }

    // 특정 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> memoModify(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                  @RequestBody MemoDto.UpdateRequest requestDto) {
        return memoService.modifyMemo(id, userDetails.getUser(), requestDto);
    }

    // 특정 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> memoRemove(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.removeMemo(id, userDetails.getUser());
    }

    // 특정 게시글 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<ResponseDto> memoLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoService.likeMemo(userDetails.getUser().getId(), id);
    }

}
