package fourth.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LIKE_COMMENT_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "COMMENT_ID")
    private Comment comment;

    //==생성 메서드==//
    public static LikeComment createLikeComment(User user, Comment comment) {
        LikeComment likeComment = LikeComment.builder()
                .build();
        likeComment.putMemo(comment);
        likeComment.putAuthor(user);
        return likeComment;
    }
    @Builder
    private LikeComment(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    //연관관계 편의 메서드
    public void putMemo(Comment comment) {
        this.comment = comment;
        comment.getLikeComment().add(this);
    }
    public void putAuthor(User user) {
        this.user = user;
        user.getLikeComment().add(this);
    }
}
