package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TitleRepository extends JpaRepository<Title, Integer> {
    // TODO : Optional로 받을 수 있다면 더 좋을듯
    Optional<Title> findByChallengeNo(int challengeNo);

    @Query("select t from Title t where t.user.no = :userNo")
    List<Title> findByUser_No(@Param("userNo") int userNo);

}
