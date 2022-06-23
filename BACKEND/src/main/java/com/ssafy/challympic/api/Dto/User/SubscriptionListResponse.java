package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.Subscription;

public class SubscriptionListResponse {
    private int subscription_no;
    private int challenge_no;
    private String challenge_title;

    public SubscriptionListResponse(Subscription subscription) {
        this.subscription_no = subscription.getSubscription_no();
        this.challenge_no = subscription.getChallenge().getNo();
        this.challenge_title = subscription.getChallenge().getTitle();
    }
}
