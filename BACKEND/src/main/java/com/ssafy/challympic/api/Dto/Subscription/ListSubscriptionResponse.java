package com.ssafy.challympic.api.Dto.Subscription;

import com.ssafy.challympic.domain.Subscription;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ListSubscriptionResponse {

    private int subscription_no;
    private int challenge_no;
    private String challenge_title;

    public ListSubscriptionResponse(Subscription subscription) {
        this.subscription_no = subscription.getNo();
        this.challenge_no = subscription.getChallenge().getNo();
        this.challenge_title = subscription.getChallenge().getTitle();
    }
}
