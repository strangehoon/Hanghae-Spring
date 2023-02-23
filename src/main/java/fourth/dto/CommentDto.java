package fourth.dto;

import lombok.Getter;
import lombok.Setter;
import fourth.entity.Comment;

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

        private Long likes;

        public Response(Comment comment) {
            this.id = comment.getId();
            this.content = comment.getContents();
            this.userName = comment.getUser().getUserName();
            this.createdAt = comment.getCreatedAt();
            this.modifiedAt = comment.getModifiedAt();
            this.likes = comment.getLikeComment().stream().count();
        }
    }
}
