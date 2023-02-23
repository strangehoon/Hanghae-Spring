package fourth.service;


import fourth.dto.basic.ResponseDto;
import fourth.dto.basic.ResponseMessage;
import fourth.dto.basic.StatusCode;
import fourth.entity.LikeMemo;
import fourth.exception.CustomException;
import fourth.repository.LikeMemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fourth.dto.MemoDto;
import fourth.entity.Memo;
import fourth.entity.User;

import fourth.jwt.JwtUtil;
import fourth.repository.MemoRepository;
import fourth.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fourth.exception.Error.*;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final LikeMemoRepository likeMemoRepository;

    private final JwtUtil jwtUtil;

    // 게시글 등록
    @Transactional
    public ResponseEntity<ResponseDto> addMemo(MemoDto.Request requestDto, User user) {

        User found = validUser(user);

        // 요청받은 DTO 로 DB에 저장할 객체 만들기
        Memo memo = memoRepository.save(Memo.createMemo(requestDto.getTitle(), requestDto.getContents(), found));
        return ResponseEntity.ok()
                .body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.SUCCESS)
                        .data(new MemoDto.Response(memo)).build());
    }
    
    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> findMemos() {

        List<Memo> memoList = memoRepository.findAllByOrderByModifiedAtDesc();
        List<MemoDto.Response> memoResponseDtoList = changeMemoToMemoDto(memoList);

        return ResponseEntity.ok()
                .body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.SUCCESS)
                        .data(memoResponseDtoList).build());
    }

    // 특정 게시글 조회
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto> findMemo(Long memoId) {
        Memo memo = validMemo(memoId);

        return ResponseEntity.ok()
                .body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.SUCCESS).data(new MemoDto.Response(memo)).build());
    }


    // 특정 게시글 수정
    @Transactional
    public ResponseEntity<ResponseDto> modifyMemo(Long memoId, User user, MemoDto.UpdateRequest requestDto) {

        Memo memo = validMemo(memoId);

        if(!memo.getUser().getId().equals(user.getId()))
            throw new CustomException(INVALID_AUTHORIZED);

        memo.update(requestDto);

        return ResponseEntity.ok().body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.SUCCESS)
                .data(new MemoDto.Response(memo)).build());
    }

    // 특정 게시글 삭제
    @Transactional
    public ResponseEntity<ResponseDto> removeMemo(Long memoId, User user) {

        Memo memo = validMemo(memoId);

        if(!memo.getUser().equals(user))
            throw new CustomException(INVALID_AUTHORIZED);

        memoRepository.deleteById(memoId);

        return ResponseEntity.ok().body(ResponseDto.builder().status(StatusCode.OK)
                .message(ResponseMessage.SUCCESS).build());
    }

    // 특정 게시글 좋아요
    @Transactional
    public ResponseEntity<ResponseDto> likeMemo(Long userId, Long memoId) {

        Memo memo = validMemo(memoId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );

        Optional<LikeMemo> found = likeMemoRepository.findByUserAndMemo(user, memo);
        if (found.isPresent()){
            likeMemoRepository.deleteById(found.get().getId());
            return ResponseEntity.ok()
                    .body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.LIKE_CANCEL).build());
        }
        else{
            LikeMemo likeMemo = LikeMemo.createLikeMemo(user, memo);
            likeMemoRepository.save(likeMemo);
            return ResponseEntity.ok()
                    .body(ResponseDto.builder().status(StatusCode.OK).message(ResponseMessage.LIKE_SUCCESS).build());
        }
    }

    // 메서드

    //db에 user가 존재하는지 검사
    private User validUser(User user) {
        User found = userRepository.findByUserName(user.getUserName()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
        return found;
    }

    //db에 memo가 존재하는지 검사
    private Memo validMemo(Long memoId) {
        Memo memo = memoRepository.findById(memoId).orElseThrow(
                () -> new CustomException(NOT_FOUND_MEMO)
        );
        return memo;
    }

    // List<Memo> -> List<MemoDto.Response>
    private static List<MemoDto.Response> changeMemoToMemoDto(List<Memo> memoList) {
        List<MemoDto.Response> memoResponseDtoList = new ArrayList<>();
        for(Memo memo : memoList)
            memoResponseDtoList.add(new MemoDto.Response(memo));
        return memoResponseDtoList;
    }

}