package second_hanghaememo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import second_hanghaememo.entity.Memo;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findAllByOrderByModifiedAtDesc();

    @Query("select m " + "from Memo m join m.user u " + "where m.id = :memoId and u.id = :userId")
    Optional<Memo> findByIdAndUserId(@Param("memoId")Long memoId, @Param("userId") Long userId);

}