package com.ssafy.challympic.api.Dto.Challenge;

import com.ssafy.challympic.domain.defaults.ChallengeType;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data // 어노테이션 변경
public class CreateChallengeRequset { // TODO: 클래스 이름 변경
    private int user_no;
    private List<String> challengers;
    private Date challenge_end;
    private ChallengeType challenge_type;
    private String challenge_title;
    private String challenge_content;
    private String title_name;
    
    // TODO: builder 추가
}
