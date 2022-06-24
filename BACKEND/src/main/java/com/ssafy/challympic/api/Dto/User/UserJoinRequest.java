package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.User;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor
public class UserJoinRequest {

    private String user_email;
    private String user_pwd;
    private String user_nickname;

    @Builder
    public UserJoinRequest(String user_email, String user_pwd, String user_nickname) {
        this.user_email = user_email;
        this.user_pwd = user_pwd;
        this.user_nickname = user_nickname;
    }

    public User toEntity(BCryptPasswordEncoder bCryptPasswordEncoder){
        return User.builder()
                .email(user_email)
                .pwd(bCryptPasswordEncoder.encode(user_pwd))
                .nickname(user_nickname)
                .build();
    }
}
