package com.ssafy.challympic.api.Dto.Challenge;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class ChallengeResponse {
    private int challenge_no;
    private int user_no;
    private LocalDateTime challenge_start;
    private Date challenge_end;
    private ChallengeAccess challenge_access;
    private ChallengeType challenge_type;
    private String challenge_title;
    private String challenge_content;
    private boolean challenge_official;
    private int challenge_report;

    @Builder
    public ChallengeResponse(Challenge challenge) {
        this.challenge_no = challenge.getNo();
        this.user_no = challenge.getUser().getNo();
        this.challenge_start = challenge.getCreatedDate();
        this.challenge_end = challenge.getEnd();
        this.challenge_access = challenge.getAccess();
        this.challenge_type = challenge.getType();
        this.challenge_title = challenge.getTitle();
        this.challenge_content = challenge.getContent();
        this.challenge_official = challenge.isOfficial();
        this.challenge_report = challenge.getReport();
    }

}
