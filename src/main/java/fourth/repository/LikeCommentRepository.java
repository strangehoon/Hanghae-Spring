package fourth.repository;

import fourth.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Optional<LikeComment> findByUserAndComment(User user, Comment comment);
}
