package com.ssafy.challympic.api.Dto.Activity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ActivityRequest {
    private int post_no;
    private int user_no;

    @Builder
    public ActivityRequest(int post_no, int user_no) {
        this.post_no = post_no;
        this.user_no = user_no;
    }
}
