package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    List<Subscription> findAllByChallengeNo(int challengeNo);

    Optional<Subscription> findByChallengeNoAndUser_No(int challengeNo, int user_no);

     List<Subscription> findAllByUserNo(int userNo);

}
