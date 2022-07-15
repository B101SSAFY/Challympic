package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Alert.AlertResponse;
import com.ssafy.challympic.api.Dto.Alert.AlertSaveRequest;
import com.ssafy.challympic.domain.Alert;
import com.ssafy.challympic.api.Dto.Result;
import com.ssafy.challympic.service.AlertService;
import com.ssafy.challympic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AlertApiController {

    private final AlertService alertService;
    private final UserService userService;

    @PostMapping("/alert")
    public Result saveAlert(@RequestBody AlertSaveRequest request){
        Alert alert = Alert.builder()
                        .user(userService.findByNo(request.getUser_no()))
                        .content(request.getAlert_content())
                        .build();
        alertService.saveAlert(alert);
        return new Result(true, HttpStatus.OK.value());
    }

    @GetMapping("/alert/{userNo}")
    public Result getAlert(@PathVariable int userNo) {
        List<AlertResponse> alertList = alertService.findAlertByUserNo(userNo);
        return new Result(true, HttpStatus.OK.value(), alertList);
    }

}
