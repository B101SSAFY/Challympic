package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Interest.InterestListResponse;
import com.ssafy.challympic.api.Dto.Interest.InterestListSaveRequest;
import com.ssafy.challympic.api.Dto.Interest.InterestSaveRequest;
import com.ssafy.challympic.api.Dto.Result;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.InterestService;
import com.ssafy.challympic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InterestApiController {

    private final InterestService interestService;
    private final UserService userService;

    @PostMapping("/user/interest/{userNo}")
    public Result save(@PathVariable("userNo") int userNo, @RequestBody InterestSaveRequest request){

        try{
            interestService.save(userNo, request.getTag_no());
            List<InterestListResponse> interestList = interestService.findByUser(userNo);
            return new Result(true, HttpStatus.OK.value(), interestList);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("/user/setInterests")
    public Result saveList(@RequestBody InterestListSaveRequest request){

        try{
            User user = userService.findByEmail(request.getUser_email());
            for (Integer t : request.getInterests()) {
                int interestNo = interestService.save(user.getNo(), t);
            }
            return new Result(true, HttpStatus.OK.value());
        }catch (Exception e){
            return new Result(true, HttpStatus.BAD_REQUEST.value());
        }
    }

    @GetMapping("/interest/{userNo}")
    public Result interestList(@PathVariable("userNo") int userNo){
        List<InterestListResponse> interestList = interestService.findByUser(userNo);
        return new Result(true, HttpStatus.OK.value(), interestList);
    }

    @DeleteMapping("/user/interest/{userNo}/{tagNo}")
    public Result delete(@PathVariable("userNo") int userNo, @PathVariable("tagNo") int tagNo){

        try {
            interestService.delete(userNo, tagNo);
            List<InterestListResponse> interestList = interestService.findByUser(userNo);
            return new Result(true, HttpStatus.OK.value(), interestList);
        }catch (Exception e){
            return new Result(true, HttpStatus.BAD_REQUEST.value());
        }
    }

}
