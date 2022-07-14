package com.ssafy.challympic.api.Dto.Challenge;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;

import java.time.LocalDateTime;
import java.util.Date;

public class SearchTrendResponse {

    int challenge_no;
    int user_no;
    LocalDateTime challenge_start;
    Date challenge_end;
    ChallengeAccess challenge_access;
    ChallengeType challenge_type;
    String challenge_title;
    String challenge_content;
    boolean challenge_official;
    int challenge_report;

    public SearchTrendResponse(Challenge challenge) {
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
