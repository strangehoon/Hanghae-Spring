package second_hanghaememo.dto;

import lombok.Getter;
import lombok.Setter;
import second_hanghaememo.entity.Comment;

import java.time.LocalDateTime;

public class CommentDto {

    @Getter
    @Setter
    public static class Request {
        private String contents;
    }

    @Getter
    @Setter
    public static class Response {

        private Long id;

        private String content;

        private String userName;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        public Response(Comment comment, String userName) {
            this.id = comment.getId();
            this.content = comment.getContents();
            this.userName = userName;
            this.createdAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();
        }
    }
}
