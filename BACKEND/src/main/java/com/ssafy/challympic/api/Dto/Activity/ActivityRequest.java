package com.ssafy.challympic.api.Dto.Activity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActivityRequest {
    private int postNo;
    private int userNo;

    @Builder
    public ActivityRequest(int postNo, int userNo) {
        this.postNo = postNo;
        this.userNo = userNo;
    }
}
