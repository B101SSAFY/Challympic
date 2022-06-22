package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.User;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@NoArgsConstructor
public class UserJoinRequest {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private String user_email;
    private String user_pwd;
    private String user_nickname;

    @Builder
    public UserJoinRequest(String user_email, String user_pwd, String user_nickname) {
        this.user_email = user_email;
        this.user_pwd = bCryptPasswordEncoder.encode(user_pwd);
        this.user_nickname = user_nickname;
    }

    public User toEntity(){
        return User.builder()
                .email(user_email)
                .pwd(bCryptPasswordEncoder.encode(user_pwd))
                .nickname(user_nickname)
                .build();
    }
}
