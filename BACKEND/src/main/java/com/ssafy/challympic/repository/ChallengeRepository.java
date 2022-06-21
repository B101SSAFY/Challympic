package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.ChallengeTag;
import com.ssafy.challympic.domain.Challenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer>{

    @Query("select c from Challenge c where c.challenge_title = :challenge_title order by c.challenge_end")
    List<Challenge> findByChallenge_titleOrderByChallenge_endDesc(@Param("challenge_title")String challenge_title);

    @Query("select c from Challenge c where c.user.user_no = :user_no")
    List<Challenge> findByUser_no(@Param("user_no") int user_no);

    @Query("select ct from ChallengeTag ct where ct.tag.tag_content = :tag_content")
    List<Challenge> findByTag_content(@Param("tag_content") String tag_content);
}
