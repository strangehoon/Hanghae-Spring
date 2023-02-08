package second_hanghaememo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import second_hanghaememo.dto.MemoForm;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memo_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name= "USER_ID")
    private User user;

    @Column(nullable = false)
    private String contents;

    //연관관계 편의 메서드
    public void putAuthor(User user) {
        this.user = user;
        user.getMemo().add(this);
    }

    //==생성 메서드==//
    public static Memo createMemo(String title, String contents, User user) {
        Memo memo = Memo.builder()
                .title(title)
                .contents(contents)
                .build();
        memo.putAuthor(user);
        return memo;
    }
    @Builder
    private Memo(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    //==비즈니스 메서드==//
    public void update(MemoForm memoForm) {
        this.title = memoForm.getTitle();
        this.contents = memoForm.getContents();
    }


    public Memo(MemoForm memoForm) {
        this.contents = memoForm.getContents();
        this.title = memoForm.getTitle();
    }

}