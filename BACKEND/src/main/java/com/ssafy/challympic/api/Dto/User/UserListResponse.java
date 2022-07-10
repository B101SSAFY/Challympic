package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.UserActive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserListResponse {
    private int user_no;
    private String user_email;
    private String user_nickname;
    private UserActive user_active;

    private int report;

    @Builder
    public UserListResponse(User user, int report) {
        this.user_no = user.getNo();
        this.user_email = user.getEmail();
        this.user_nickname = user.getNickname();
        this.user_active = user.getActive();
        this.report = report;
    }
}
