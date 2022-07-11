package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.Subscription;

// TODO: Getter, NoArgsConst
public class SubscriptionListResponse {
    private int subscription_no;
    private int challenge_no;
    private String challenge_title;

    // TODO : builder
    public SubscriptionListResponse(Subscription subscription) {
        this.subscription_no = subscription.getNo();
        this.challenge_no = subscription.getChallenge().getNo();
        this.challenge_title = subscription.getChallenge().getTitle();
    }
}
