package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.ChallengeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer>{

    @Query("select c from Challenge c where c.title = :title order by c.end")
    List<Challenge> findByTitleOrderByEndDesc(@Param("title")String title);

    @Query("select c from Challenge c where c.user.no = ?1")
    List<Challenge> findByUserNo(int userNo);

    @Query("select ct.challenge from ChallengeTag ct where ct.tag.content = :content")
    List<Challenge> findByTagContent(@Param("content") String content);

    @Query("select c from Challenge c where c.title = :title")
    List<Challenge> findByTitle(@Param("title")String title);
}
