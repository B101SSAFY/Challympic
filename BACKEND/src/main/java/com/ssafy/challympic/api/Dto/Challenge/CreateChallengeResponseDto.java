package com.ssafy.challympic.api.Dto.Challenge;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // TODO: 어노테이션 변경
@AllArgsConstructor
public class CreateChallengeResponseDto {
    private int challenge_no;
    
    // TODO: builder 추가
}
