package com.ssafy.challympic.api.Dto.Challenge;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChallengeRequest {
    private int challenge_no;

    @Builder
    public ChallengeRequest(int challenge_no) {
        this.challenge_no = challenge_no;
    }
}
