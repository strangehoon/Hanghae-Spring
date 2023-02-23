package fourth.repository;

import fourth.entity.LikeMemo;
import fourth.entity.Memo;
import fourth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeMemoRepository extends JpaRepository<LikeMemo, Long> {

    Optional<LikeMemo> findByUserAndMemo(User user, Memo memo);
}
