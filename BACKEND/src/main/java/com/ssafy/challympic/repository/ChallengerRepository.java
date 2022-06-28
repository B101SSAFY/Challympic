package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengerRepository extends JpaRepository<Challenger, Integer> {

    List<Challenger> findByChallenge_No(int challenge_no);
}
