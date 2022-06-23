package com.ssafy.challympic.api.Dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdatePwdRequest {

    private String user_pwd;
    private String user_newpwd;

    @Builder
    public UserUpdatePwdRequest(String user_pwd, String user_newpwd) {
        this.user_pwd = user_pwd;
        this.user_newpwd = user_newpwd;
    }
}
