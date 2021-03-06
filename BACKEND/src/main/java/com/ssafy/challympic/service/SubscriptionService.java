package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Subscription.ListSubscriptionResponse;
import com.ssafy.challympic.domain.Subscription;
import com.ssafy.challympic.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void deleteSubscription(int challengeNo, int userNo) {
        Subscription subscription = subscriptionRepository.findByChallengeNoAndUser_No(challengeNo, userNo).orElseThrow(() -> new NoSuchElementException("존재하지 않는 구독정보입니다."));
        subscriptionRepository.delete(subscription);
    }

    public int findSubscriptionByChallenge(int challenge_no){
        return subscriptionRepository.findAllByChallengeNo(challenge_no).size();
    }

    public Subscription findSubscriptionByChallengeAndUser(int challengeNo, int userNo) {
        return subscriptionRepository.findByChallengeNoAndUser_No(challengeNo, userNo).orElseThrow(() -> new NoSuchElementException("존재하지 않는 구독 정보입니다."));
    }

    public List<ListSubscriptionResponse> findSubscriptionByUser(int userNo) {
        List<Subscription> subscriptionList = subscriptionRepository.findAllByUserNo(userNo);
        List<ListSubscriptionResponse> subscriptionDtoList = new ArrayList<>();
        subscriptionDtoList = subscriptionList.stream()
                .map(ListSubscriptionResponse::new)
                .collect(Collectors.toList());
        return subscriptionDtoList;
    }

    public boolean validSubscription(int challengeNo, int userNo){
        return (subscriptionRepository.findByChallengeNoAndUser_No(challengeNo, userNo).isPresent());
    }
}
