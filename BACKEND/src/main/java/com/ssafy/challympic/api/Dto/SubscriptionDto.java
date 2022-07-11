package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data // TODO : 어노테이션 수정
@AllArgsConstructor
public class SubscriptionDto {
    private int subscription_no;
    private int challenge_no;
    private String challenge_title;

    // TODO : builder 추가
    public SubscriptionDto(Subscription subscription) {
        this.subscription_no = subscription.getNo();
        this.challenge_no = subscription.getChallenge().getNo();
        this.challenge_title = subscription.getChallenge().getTitle();
    }
}
