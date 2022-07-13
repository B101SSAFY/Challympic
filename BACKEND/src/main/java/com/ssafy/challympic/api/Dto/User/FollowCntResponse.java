package com.ssafy.challympic.api.Dto.User;

import lombok.Builder;
import lombok.NoArgsConstructor;

// TODO : Getter?
@NoArgsConstructor
public class FollowCntResponse {
    private int followerCnt;
    private int followingCnt;

    @Builder
    public FollowCntResponse(int followerCnt, int followingCnt) {
        this.followerCnt = followerCnt;
        this.followingCnt = followingCnt;
    }
}
