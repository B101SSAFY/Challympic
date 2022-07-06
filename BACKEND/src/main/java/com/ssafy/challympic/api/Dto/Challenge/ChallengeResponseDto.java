package com.ssafy.challympic.api.Dto.Challenge;

import com.ssafy.challympic.api.Dto.PostDto;
import com.ssafy.challympic.api.Dto.UserDto;
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
public class ChallengeResponseDto {
    private int challenge_no;
    private int user_no;
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

    @Builder
    public ChallengeResponseDto(Challenge challenge) {
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

    public ChallengeResponseDto update(List<UserDto> challengers, List<PostDto> postList){
        this.challenge_challengers = challengers;
        this.postList = postList;
        return this;
    }
}
