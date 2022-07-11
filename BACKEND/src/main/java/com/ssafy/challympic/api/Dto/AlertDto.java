package com.ssafy.challympic.api.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data // TODO : 어노테이션 2개 수정
@AllArgsConstructor
public class AlertDto {
    private int user_no;
    private String alert_content;
    private boolean alert_confirm;
    private LocalDateTime alert_regDate;
    
    // TODO : builder 추가
}
