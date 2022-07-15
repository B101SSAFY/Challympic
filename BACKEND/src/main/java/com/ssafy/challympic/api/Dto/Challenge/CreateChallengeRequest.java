package com.ssafy.challympic.api.Dto.Challenge;

import com.ssafy.challympic.domain.defaults.ChallengeType;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class CreateChallengeRequest {
    private int user_no;
    private List<String> challengers;
    private Date challenge_end;
    private ChallengeType challenge_type;
    private String challenge_title;
    private String challenge_content;
    private String title_name;
}
