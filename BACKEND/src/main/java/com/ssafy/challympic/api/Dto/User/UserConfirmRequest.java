package com.ssafy.challympic.api.Dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserConfirmRequest {
    private String user_email;
    private String user_nickname;

    @Builder
    public UserConfirmRequest(String user_email, String user_nickname) {
        this.user_email = user_email;
        this.user_nickname = user_nickname;
    }
}
