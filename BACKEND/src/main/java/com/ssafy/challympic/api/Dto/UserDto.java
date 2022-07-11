package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // TODO : 어노테이션 수정
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int user_no;
    private String user_email;
    private String user_nickname;

    // TODO : builder 추가
    public UserDto(User user) {
        this.user_no = user.getNo();
        this.user_email = user.getEmail();
        this.user_nickname = user.getNickname();
    }
}
