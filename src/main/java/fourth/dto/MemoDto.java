package fourth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import fourth.dto.CommentDto;
import fourth.entity.Comment;
import fourth.entity.Memo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MemoDto {
    @Getter
    @Setter
    public static class Request {

        private String title;
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

        private Long likes;

        private List<CommentDto.Response> comment = new ArrayList<CommentDto.Response>();

        public Response(Memo memo) {
            this.id = memo.getId();
            this.modifiedAt = memo.getModifiedAt();
            this.title = memo.getTitle();
            this.userName = memo.getUser().getUserName();
            this.createdAt =memo.getCreatedAt();
            this.contents = memo.getContents();
            this.likes = memo.getLikeMemo().stream().count();

            List<Comment> comments = new ArrayList<>(memo.getComment());
            List<CommentDto.Response> commentResponseDtos = new ArrayList<>();

            for(Comment comment : comments){
                commentResponseDtos.add(new CommentDto.Response(comment));
            }
            this.comment = commentResponseDtos;
            commentResponseDtos.sort(Comparator.comparing(CommentDto.Response::getCreatedAt).reversed());
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
