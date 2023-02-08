package second_hanghaememo.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoDto {
    private String title;

    private String userName;

    private LocalDateTime createdAt;

    private String contents;

    public MemoDto(String title, String userName, LocalDateTime createdAt, String contents) {
        this.title = title;
        this.userName = userName;
        this.createdAt =createdAt;
        this.contents = contents;
    }

    public MemoDto(String title, String userName, String contents) {
        this.title = title;
        this.userName = userName;
        this.contents = contents;
    }
}
