package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    List<Subscription> findByChallenge_No(int challengeNo);

    @Query("select s from Subscription s where s.challenge.no = :challengeNo and s.user.no = :userNo")
    Subscription findByChallengeNoAndUserNo(@Param("challengeNo") int challengeNo, @Param("userNo") int userNo);

     List<Subscription> findByUser_No(int userNo);
}
