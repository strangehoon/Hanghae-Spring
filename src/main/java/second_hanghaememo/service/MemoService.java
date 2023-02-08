package second_hanghaememo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import second_hanghaememo.dto.Check;
import second_hanghaememo.dto.MemoDto;
import second_hanghaememo.dto.MemoForm;
import second_hanghaememo.entity.Memo;
import second_hanghaememo.entity.User;
import second_hanghaememo.repository.MemoRepository;
import second_hanghaememo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    @Transactional
    public MemoDto createMemo(Long userId, MemoForm memoForm) {
        Optional<User> foundUser = userRepository.findById(userId);

        if(foundUser.isEmpty()){
            User user = User.createUser(memoForm.getUserName(), memoForm.getPassword());
            User savedUser = userRepository.save(user);
            Memo memo = Memo.createMemo(memoForm.getTitle(), memoForm.getContents(), savedUser);
            Memo savedMemo = memoRepository.save(memo);
            MemoDto memoDto = new MemoDto(memo.getTitle(), user.getUserName(), memo.getCreatedAt(), memo.getContents());
            return memoDto;
        }
        else {
            Memo memo = Memo.createMemo(memoForm.getTitle(), memoForm.getContents(), foundUser.get());
            Memo savedMemo = memoRepository.save(memo);
            MemoDto memoDto = new MemoDto(memo.getTitle(), foundUser.get().getUserName(), memo.getCreatedAt(), memo.getContents());
            return memoDto;
        }
    }

    @Transactional(readOnly = true)
    public List<MemoDto> getMemos(Long userId) {
        return memoRepository.findAllByOrderByCreatedAtDesc(userId);
    }

    @Transactional(readOnly = true)
    public MemoDto findMemo(Long memoId, Long userId) {
        return memoRepository.findMemoDtoOne(memoId, userId);
    }

    @Transactional
    public MemoDto updateMemo(Long memoId, Long userId, MemoForm memoForm) {
        User foundUser = userRepository.findById(userId).get();
        Memo memo = memoRepository.findById(memoId).get();
        if(foundUser.getPassword().equals(memoForm.getPassword())){
            memo.update(memoForm);
            MemoDto memoDto = new MemoDto(memo.getTitle(), foundUser.getUserName(), memo.getCreatedAt(), memo.getContents());
            return memoDto;
        }
        MemoDto memoDto = new MemoDto(memo.getTitle(), foundUser.getUserName(),memo.getCreatedAt(), memo.getContents());

        return memoDto;
    }
    @Transactional
    public Check deleteMemo(Long memoId, Long userId, String password) {
        User foundUser = userRepository.findById(userId).get();
        if(foundUser.getPassword().equals(password)){
            memoRepository.deleteById(memoId);
            Check check = new Check("success");
            return check;
        }
        else{
            Check check = new Check("false");
            return check;
        }
    }
}