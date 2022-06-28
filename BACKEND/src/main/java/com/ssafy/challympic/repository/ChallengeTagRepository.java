package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.ChallengeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChallengeTagRepository extends JpaRepository<ChallengeTag, Integer> {

    List<ChallengeTag> findByChallenge_No(int challengeNo);
}
