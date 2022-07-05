package com.ssafy.challympic.api.Dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {
    private int user_no;

    @Builder
    public UserRequest(int user_no) {
        this.user_no = user_no;
    }
}
