package com.ssafy.challympic.api.Dto.Challenge;

import com.ssafy.challympic.api.Dto.User.ChallengerDto;
import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChallengeInfoResponse {

    int challenge_no;
    int user_no;
    LocalDateTime challenge_start;
    Date challenge_end;
    ChallengeAccess challenge_access;
    ChallengeType challenge_type;
    String challenge_title;
    String challenge_content;
    List<ChallengerDto> challenge_challengers;
    boolean challenge_official;
    int challenge_report;

    public ChallengeInfoResponse(Challenge challenge, List<ChallengerDto> challengers) {
        this.challenge_no = challenge.getNo();
        this.user_no = challenge.getUser().getNo();
        this.challenge_start = challenge.getCreatedDate();
        this.challenge_end = challenge.getEnd();
        this.challenge_access = challenge.getAccess();
        this.challenge_type = challenge.getType();
        this.challenge_title = challenge.getTitle();
        this.challenge_content = challenge.getContent();
        this.challenge_challengers = challengers;
        this.challenge_official = challenge.isOfficial();
        this.challenge_report = challenge.getReport();
    }

}
