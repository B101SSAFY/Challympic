package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    @Query("select s from Subscription s where s.challenge.no = :challengeNo")
    List<Subscription> findByChallengeNo(@Param("challengeNo") int challengeNo);

    @Query("select s from Subscription s where s.challenge.no = :challengeNo and s.user.no = :userNo")
    Subscription findByChallengeNoAndUserNo(@Param("challengeNo") int challengeNo, @Param("userNo") int userNo);

    @Query("select s from Subscription s where s.user.no = :userNo")
     List<Subscription> findByUserNo(int userNo);
}
