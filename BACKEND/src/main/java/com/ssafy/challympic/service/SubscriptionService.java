package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.SubscriptionDto;
import com.ssafy.challympic.domain.Subscription;
import com.ssafy.challympic.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void deleteSubscription(int challengeNo, int userNo) {
        Subscription subscription = subscriptionRepository.findByChallengeNoAndUser_No(challengeNo, userNo).get();
        subscriptionRepository.delete(subscription);
    }

    public int findSubscriptionByChallenge(int challenge_no){
        return subscriptionRepository.findAllByChallengeNo(challenge_no).size();
    }

    public Subscription findSubscriptionByChallengeAndUser(int challengeNo, int userNo) {
        return subscriptionRepository.findByChallengeNoAndUser_No(challengeNo, userNo).get();
    }

    public List<SubscriptionDto> findSubscriptionByUser(int userNo) {
        List<Subscription> subscriptionList = subscriptionRepository.findAllByUserNo(userNo);
        List<SubscriptionDto> subscriptionDtoList = new ArrayList<>();
        subscriptionDtoList = subscriptionList.stream()
                .map(s -> new SubscriptionDto(s))
                .collect(Collectors.toList());
        return subscriptionDtoList;
    }

    public boolean validSubscription(int challengeNo, int userNo){
        return (subscriptionRepository.findByChallengeNoAndUser_No(challengeNo, userNo) != null);
    }
}
