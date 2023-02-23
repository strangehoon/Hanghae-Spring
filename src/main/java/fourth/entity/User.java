package fourth.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import fourth.entity.Comment;
import fourth.entity.Memo;
import fourth.entity.UserRoleEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Memo> memo = new ArrayList<Memo>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();


    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    List<LikeMemo> likeMemo = new ArrayList<LikeMemo>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    List<LikeComment> likeComment = new ArrayList<LikeComment>();


    //==생성 메서드==//
    public static User createUser(String userName, String password, UserRoleEnum role) {
        User user = User.builder()
                .userName(userName)
                .password(password)
                .role(role)
                .build();
        return user;
    }
    @Builder
    private User(String userName, String password, UserRoleEnum role) {
        this.role = role;
        this.userName = userName;
        this.password = password;
    }

}
