package second_hanghaememo.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import second_hanghaememo.dto.MemoDto;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memo_id")
    private Long id;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "USER_ID")
    private User user;

    @OneToMany(mappedBy = "memo", cascade = CascadeType.REMOVE)
    List<Comment> comment = new ArrayList<Comment>();

    @Column
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
    public void update(MemoDto.UpdateRequest requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }


    public Memo(MemoDto.Request requestDto) {
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
    }

}