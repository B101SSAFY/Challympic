package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Challenge.ChallengeResponseDto;
import com.ssafy.challympic.api.Dto.Challenge.ChallengeTitleCheckRequsetDto;
import com.ssafy.challympic.api.Dto.Challenge.CreateChallengeRequset;
import com.ssafy.challympic.api.Dto.Challenge.CreateChallengeResponseDto;
import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.SubscriptionDto;
import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.service.ChallengeService;
import com.ssafy.challympic.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChallengeApiController {

    private final ChallengeService challengeService;
    private final SubscriptionService subscriptionService;

    /**
     * 챌린지 목록
     */
    @GetMapping("/challenge")
    public Result challenges() {
        List<ChallengeResponseDto> challengeList = challengeService.getChallenges();
        return new Result(true, HttpStatus.OK.value(), challengeList);
    }

    /**
     * 챌린지 등록
     */
    @PostMapping("/challenge")
    public Result createChallenge(@RequestBody CreateChallengeRequset request) {
        Challenge challenge = challengeService.saveChallenge(request);
        return new Result(true, HttpStatus.OK.value(), new CreateChallengeResponseDto(challenge.getNo()));
    }

    @PostMapping("/challenge/confirm")
    public Result ChallengeTitleCheck(@RequestBody ChallengeTitleCheckRequsetDto request) {
        try{
            challengeService.validateDuplicateTitle(request); // TODO : boolean으로 수정
        }catch (Exception e) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
        return new Result(true, HttpStatus.OK.value());
    }

    /**
     * 구독추가
     */
    @PostMapping("/challenge/{challengeNo}/subscribe/{no}")
    public Result addSubscription(@PathVariable int challengeNo, @PathVariable int no) {
        List<SubscriptionDto> subscriptionDtoList;
        try{
            subscriptionDtoList = challengeService.addSubscription(challengeNo, no);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
        return new Result(true, HttpStatus.OK.value(), subscriptionDtoList);
    }

    /**
     * 챌린지 번호로 챌린지 가져오기
     */
    @GetMapping("/challenge/{challengeNo}")
    public Result getChallengeInfoByChallengeNo(@PathVariable int challengeNo) {
        ChallengeDto challengeResponse;
        try{
            challengeResponse = challengeService.getChallengeInfo(challengeNo);
        }catch (Exception e) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
        return new Result(true, HttpStatus.OK.value(), challengeResponse);
    }

    /**
     *  구독 여부 확인
     * */
    @GetMapping("/challenge/{challengeNo}/subscribe/{userNo}")
    public Result isSubscription(@PathVariable int challengeNo, @PathVariable int userNo){
        if(subscriptionService.validSubscription(challengeNo, userNo))
            return new Result(true, HttpStatus.OK.value());

        return new Result(false, HttpStatus.OK.value());
    }


    /**
     * 구독 취소
     */
    @DeleteMapping("/challenge/{challengeNo}/subscribe/{no}")
    public Result removeSubscription(@PathVariable int challengeNo, @PathVariable int no) {
        try{
            subscriptionService.deleteSubscription(challengeNo, no);
        }catch (Exception e) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
        return new Result(true, HttpStatus.OK.value());
    }

    /**
     * 구독 목록
     */
    @GetMapping("/subscribe/{userNo}")
    public Result getSubscription(@PathVariable int userNo) {
        List<SubscriptionDto> subscriptions;
        try{
            subscriptions = subscriptionService.findSubscriptionByUser(userNo);
        }catch (Exception e) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
        return new Result(true, HttpStatus.OK.value(), subscriptions);
    }
}