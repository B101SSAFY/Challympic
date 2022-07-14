package com.ssafy.challympic.api.Dto.Alert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlertSaveRequest {
    private int user_no;
    private String alert_content;
}
