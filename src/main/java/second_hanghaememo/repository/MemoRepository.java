package second_hanghaememo.repository;


import com.second_hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import second_hanghaememo.dto.MemoDto;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<second_hanghaememo.entity.Memo, Long> {
    @Query("select new com.sparta.hanghaememo.dto.MemoDto(m.title, u.userName, m.createdAt, m.contents) " + "from Memo m join m.user u " + "where u.id= :userId")
    List<second_hanghaememo.dto.MemoDto> findAllByOrderByCreatedAtDesc(@Param("userId")Long userId);

    @Query("select new com.sparta.hanghaememo.dto.MemoDto(m.title, u.userName, m.createdAt, m.contents) " + "from Memo m join m.user u " + "where m.id = :memoId and u.id = :userId")
    MemoDto findMemoDtoOne(@Param("memoId")Long memoId, @Param("userId")Long userId );

}