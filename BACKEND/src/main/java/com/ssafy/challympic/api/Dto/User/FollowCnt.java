package com.ssafy.challympic.api.Dto.User;

import lombok.Builder;
import lombok.NoArgsConstructor;

// TODO : Getter?
@NoArgsConstructor
public class FollowCnt {
    private int followerCnt;
    private int followingCnt;

    @Builder
    public FollowCnt(int followerCnt, int followingCnt) {
        this.followerCnt = followerCnt;
        this.followingCnt = followingCnt;
    }
}
