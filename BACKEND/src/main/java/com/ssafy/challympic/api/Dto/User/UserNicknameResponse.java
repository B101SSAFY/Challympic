package com.ssafy.challympic.api.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNicknameResponse {

    private int user_no;
    private String user_nickname;

    @Builder
    public UserNicknameResponse(String user_nickname) {
        this.user_nickname = user_nickname;
    }
}
