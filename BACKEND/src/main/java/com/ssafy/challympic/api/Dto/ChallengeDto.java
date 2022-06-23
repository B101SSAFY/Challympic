package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ChallengeDto {
    private int challenge_no;
    private int user_no;
    private Date challenge_start;
    private Date challenge_end;
    private ChallengeAccess challenge_access;
    private ChallengeType challenge_type;
    private String challenge_title;
    private String challenge_content;
    private List<UserDto> challenge_challengers;
    private boolean challenge_official;
    private int challenge_report;
    private List<PostDto> postList;
    private boolean isSubscription;

    public ChallengeDto(Challenge challenge) {
        this.challenge_no = challenge.getNo();
        this.user_no = challenge.getUser().getNo();
        this.challenge_start = challenge.getStart();
        this.challenge_end = challenge.getEnd();
        this.challenge_access = challenge.getAccess();
        this.challenge_type = challenge.getType();
        this.challenge_title = challenge.getTitle();
        this.challenge_content = challenge.getContent();
        this.challenge_official = challenge.isOfficial();
        this.challenge_report = challenge.getReport();
    }

    public ChallengeDto(Challenge challenge, List<UserDto> challengers) {
        this.challenge_no = challenge.getNo();
        this.user_no = challenge.getUser().getNo();
        this.challenge_start = challenge.getStart();
        this.challenge_end = challenge.getEnd();
        this.challenge_access = challenge.getAccess();
        this.challenge_type = challenge.getType();
        this.challenge_title = challenge.getTitle();
        this.challenge_content = challenge.getContent();
        this.challenge_challengers = challengers;
        this.challenge_official = challenge.isOfficial();
        this.challenge_report = challenge.getReport();
    }

    public ChallengeDto(Challenge challenge, boolean isSubscription) {
        this.challenge_no = challenge.getNo();
        this.user_no = challenge.getUser().getNo();
        this.challenge_start = challenge.getStart();
        this.challenge_end = challenge.getEnd();
        this.challenge_access = challenge.getAccess();
        this.challenge_type = challenge.getType();
        this.challenge_title = challenge.getTitle();
        this.challenge_content = challenge.getContent();
        this.challenge_official = challenge.isOfficial();
        this.challenge_report = challenge.getReport();
        this.isSubscription = isSubscription;
    }

    public ChallengeDto(Challenge challenge, List<PostDto> postList, boolean isSubscription) {
        this.challenge_no = challenge.getNo();
        this.user_no = challenge.getUser().getNo();
        this.challenge_start = challenge.getStart();
        this.challenge_end = challenge.getEnd();
        this.challenge_access = challenge.getAccess();
        this.challenge_type = challenge.getType();
        this.challenge_title = challenge.getTitle();
        this.challenge_content = challenge.getContent();
        this.challenge_official = challenge.isOfficial();
        this.challenge_report = challenge.getReport();
        this.postList = postList;
        this.isSubscription = isSubscription;
    }
}