package fourth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import fourth.entity.Comment;

import java.util.Optional;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c " + "from Comment c join c.user u " + "where c.id = :commentId and u.id = :userId")
    Optional<Comment> findByIdAndUserId(@Param("commentId")Long commentId, @Param("userId") Long userId);

}
