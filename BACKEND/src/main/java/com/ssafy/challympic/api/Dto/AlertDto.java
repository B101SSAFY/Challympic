package com.ssafy.challympic.api.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class AlertDto {
    private int user_no;
    private String alert_content;
    private boolean alert_confirm;
    private LocalDateTime alert_regDate;
}
