package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<Title, Integer> {
    @Query("select t from Title t where t.challenge.no = :challengeNo")
    Title findByChallenge(@Param("challengeNo") int challengeNo);

    @Query("select t from Title t where t.user.no = :userNo")
    List<Title> findTitleByUserNo(@Param("userNo") int userNo);
}
