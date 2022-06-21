package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.ChallengeTag;
import com.ssafy.challympic.domain.Challenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Integer>{

    List<Challenge> findAll();

    Challenge save(Challenge challenge);

    List<Challenge> findByTitleOrderByChallenge_endDesc(@Param("challenge_title")String challenge_title);

    Challenger save(Challenger challenger);

    void delete(Challenger challenger);

    List<Challenge> findByUser_no(int userNo);

    Challenge findByChallenge_no(int challengeNo);

    void save(ChallengeTag challengeTag);

    List<Challenge> findByTag_content(String tag_content);
}
