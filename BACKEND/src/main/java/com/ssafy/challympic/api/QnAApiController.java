package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.QnA.QnADto;
import com.ssafy.challympic.api.Dto.QnA.QnARequest;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.QnAService;
import com.ssafy.challympic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QnAApiController {

    private final QnAService qnAService;
    private final UserService userService;

    @PostMapping("/user/{userNo}/qna")
    public Result question(@PathVariable("userNo") int user_no, @RequestBody QnARequest request){
        User user = userService.findByNo(user_no);
        qnAService.save(request, user);
        Result collect = qnaList(user_no);
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @GetMapping("/user/{userNo}/qna")
    public Result qnaList(@PathVariable("userNo") int user_no){
        List<QnADto> qnaList = qnAService.findByUser(user_no);
        return new Result(true, HttpStatus.OK.value(), qnaList);
    }

}
