package com.ssafy.challympic.api.Dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

    private String user_nickname;
    private String user_pwd;
    private String user_newpwd;
    private MultipartFile file;
    private String user_title;

    @Builder
    public UserUpdateRequest(String user_nickname, String user_pwd, String user_newpwd, MultipartFile file, String user_title) {
        this.user_nickname = user_nickname;
        this.user_pwd = user_pwd;
        this.user_newpwd = user_newpwd;
        this.file = file;
        this.user_title = user_title;
    }
}
