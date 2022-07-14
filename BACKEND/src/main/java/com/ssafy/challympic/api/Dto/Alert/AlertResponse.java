package com.ssafy.challympic.api.Dto.Alert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponse {
    private int user_no;
    private String alert_content;
    private boolean alert_confirm;
    private LocalDateTime alert_regDate;
}
