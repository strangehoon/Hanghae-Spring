package second_hanghaememo.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
