package com.ssafy.challympic.api.Dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowRequest {

    private int follow_follower_no;

    @Builder
    public FollowRequest(int follow_follower_no) {
        this.follow_follower_no = follow_follower_no;
    }
}
