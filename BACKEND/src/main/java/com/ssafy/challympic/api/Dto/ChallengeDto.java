package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChallengeDto {
    private int challenge_no;
    private int user_no;
    private String user_email;
    private LocalDateTime challenge_start;
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
    private int post_cnt;
    private int subscription_cnt;

    public ChallengeDto(Challenge challenge) {
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

    public ChallengeDto(Challenge challenge, List<UserDto> challengers) {
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

    public ChallengeDto(Challenge challenge, boolean isSubscription) {
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
        this.isSubscription = isSubscription;
    }

    public ChallengeDto(Challenge challenge, List<PostDto> postList, boolean isSubscription) {
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
        this.postList = postList;
        this.isSubscription = isSubscription;
    }

    @Builder
    public ChallengeDto(int challenge_no, int user_no, String user_email, LocalDateTime challenge_start, Date challenge_end, ChallengeAccess challenge_access, ChallengeType challenge_type, String challenge_title, String challenge_content, List<UserDto> challenge_challengers, boolean challenge_official, int challenge_report, List<PostDto> postList, boolean isSubscription, int post_cnt, int subscription_cnt) {
        this.challenge_no = challenge_no;
        this.user_no = user_no;
        this.user_email = user_email;
        this.challenge_start = challenge_start;
        this.challenge_end = challenge_end;
        this.challenge_access = challenge_access;
        this.challenge_type = challenge_type;
        this.challenge_title = challenge_title;
        this.challenge_content = challenge_content;
        this.challenge_challengers = challenge_challengers;
        this.challenge_official = challenge_official;
        this.challenge_report = challenge_report;
        this.postList = postList;
        this.isSubscription = isSubscription;
        this.post_cnt = post_cnt;
        this.subscription_cnt = subscription_cnt;
    }

    public ChallengeDto update(int post_cnt, int subscription_cnt){
        this.post_cnt = post_cnt;
        this.subscription_cnt = subscription_cnt;
        return this;
    }
}