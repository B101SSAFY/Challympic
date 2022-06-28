package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengerRepository extends JpaRepository<Challenger, Integer> {

    @Query("select c from Challenger c where c.challenge = :challenge_no")
    List<Challenger> findByChallengeNo(int challenge_no);
}
