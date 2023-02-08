package second_hanghaememo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user")
    List<Memo> memo = new ArrayList<Memo>();

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;


    //==생성 메서드==//
    public static User createUser(String userName, String password) {
        User user = User.builder()
                .userName(userName)
                .password(password)
                .build();
        return user;
    }
    @Builder
    private User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
