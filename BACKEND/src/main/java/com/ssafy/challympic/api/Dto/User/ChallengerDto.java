package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChallengerDto {

    private int user_no;
    private String user_email;
    private String user_nickname;

    public ChallengerDto(User user) {
        this.user_no = user.getNo();
        this.user_email = user.getEmail();
        this.user_nickname = user.getNickname();
    }
}
