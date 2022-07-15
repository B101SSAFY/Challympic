package com.ssafy.challympic.api.Dto.User;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UserUpdateRequest {

    private String user_nickname;
    private String user_title;
    private MultipartFile file;

    @Builder
    public UserUpdateRequest(String user_nickname, String user_title, MultipartFile file) {
        this.user_nickname = user_nickname;
        this.user_title = user_title;
        this.file = file;
    }
}
