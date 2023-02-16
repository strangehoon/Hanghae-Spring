package second_hanghaememo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import second_hanghaememo.entity.Comment;
import second_hanghaememo.entity.Memo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemoDto {
    @Getter
    @Setter
    public static class Request {

        private String title;

        private String userName;

        private String contents;

    }

    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String title;

        private String userName;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;
        private String contents;

        private List<CommentDto.Response> comment = new ArrayList<CommentDto.Response>();

        public Response(Memo memo) {
            this.id = memo.getId();
            this.modifiedAt = memo.getModifiedAt();
            this.title = memo.getTitle();
            this.userName = memo.getUser().getUserName();
            this.createdAt =memo.getCreatedAt();
            this.contents = memo.getContents();
            List<Comment> comments = new ArrayList<>(memo.getComment());
            List<CommentDto.Response> commentResponseDtos = new ArrayList<>();
            for(Comment comment : comments){
                commentResponseDtos.add(new CommentDto.Response(comment, userName));
            }
            this.comment = commentResponseDtos;
        }

    }

    @Getter
    @Setter
    public static class UpdateRequest {

        private String title;
        private String contents;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DeleteResponse {

        private String message;
        private int status;

        public DeleteResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }
    }

}
