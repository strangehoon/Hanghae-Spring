package fourth.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeMemo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LIKE_MEMO_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "MEMO_ID")
    private Memo memo;

    //==생성 메서드==//
    public static LikeMemo createLikeMemo(User user, Memo memo) {
        LikeMemo likeMemo = LikeMemo.builder()
                .build();
        likeMemo.putMemo(memo);
        likeMemo.putAuthor(user);
        return likeMemo;
    }
    @Builder
    private LikeMemo(User user, Memo memo) {
        this.user = user;
        this.memo = memo;
    }

    //연관관계 편의 메서드
    public void putMemo(Memo memo) {
        this.memo = memo;
        memo.getLikeMemo().add(this);
    }
    public void putAuthor(User user) {
        this.user = user;
        user.getLikeMemo().add(this);
    }
}
