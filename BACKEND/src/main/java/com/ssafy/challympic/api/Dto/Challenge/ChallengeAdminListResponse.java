package com.ssafy.challympic.api.Dto.Challenge;

import com.ssafy.challympic.api.Dto.PostDto;
import com.ssafy.challympic.api.Dto.UserDto;
import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class ChallengeAdminListResponse {
    private int challenge_no;

    private String user_email;

    private LocalDateTime challenge_start;

    private Date challenge_end;

    private String challenge_title;

    private boolean challenge_official;

    private int challenge_report;

    private int post_cnt;

    private int subscription_cnt;

    @Builder
    public ChallengeAdminListResponse(Challenge challenge, int post_cnt, int subscription_cnt) {
        this.user_email = challenge.getUser().getEmail();
        this.challenge_no = challenge.getNo();
        this.challenge_title = challenge.getTitle();
        this.challenge_start = challenge.getCreatedDate();
        this.challenge_end = challenge.getEnd();
        this.challenge_official = challenge.isOfficial();
        this.challenge_report = challenge.getReport();
        this.post_cnt = post_cnt;
        this.subscription_cnt = subscription_cnt;
    }
}
