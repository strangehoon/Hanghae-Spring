package second_hanghaememo.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    @Column
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "MEMO_ID")
    private Memo memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "USER_ID")
    private User user;

    //연관관계 편의 메서드
    public void putMemo(Memo memo) {
        this.memo = memo;
        memo.getComment().add(this);
    }
    public void putAuthor(User user) {
        this.user = user;
        user.getComment().add(this);
    }

    //==생성 메서드==//
    public static Comment createComment(String contents, User user, Memo memo) {
        Comment comment = Comment.builder()
                .contents(contents)
                .build();
        comment.putAuthor(user);
        comment.putMemo(memo);
        return comment;
    }
    @Builder
    private Comment(String contents, User user, Memo memo) {
        this.contents = contents;
    }

    // 비즈니스 메서드
    public void update(String contents){
        this.contents = contents;
    }
}
