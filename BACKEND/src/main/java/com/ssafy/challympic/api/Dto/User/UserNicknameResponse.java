package com.ssafy.challympic.api.Dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserNicknameResponse {

    private int user_no;
    private String user_nickname;

    @Builder
    public UserNicknameResponse(int user_no, String user_nickname) {
        this.user_no = user_no;
        this.user_nickname = user_nickname;
    }
}
