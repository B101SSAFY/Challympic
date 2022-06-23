package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Challenge.ChallengeTitleCheckRequsetDto;
import com.ssafy.challympic.api.Dto.Challenge.CreateChallengeRequset;
import com.ssafy.challympic.api.Dto.Challenge.CreateChallengeResponseDto;
import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.Challenge.ChallengeResponseDto;
import com.ssafy.challympic.api.Dto.SubscriptionDto;
import com.ssafy.challympic.api.Dto.UserDto;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChallengeApiController {

    private final ChallengeService challengeService;
    private final UserService userService;
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
//        challengeService.validateDuplicateTitle(request);
        List<Challenge> challenges = challengeService.findChallengeByTitle(request.getChallenge_title());

        if(challenges.size() != 0){
            return new Result(false, HttpStatus.FORBIDDEN.value());
        }

        for(Challenge c : challenges) {
            if(c.getEnd().after(new Date())){
                return new Result(false, HttpStatus.FORBIDDEN.value());
            }
        }
        return new Result(true, HttpStatus.OK.value());
    }

    /**
     * 구독추가
     */
    @PostMapping("/challenge/{challengeNo}/subscribe/{no}")
    public Result addSubscription(@PathVariable int challengeNo, @PathVariable int no) {
        Challenge challenge = challengeService.findChallengeByChallengeNo(challengeNo);
        if(challenge == null) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
        User user = userService.findByNo(no);

        Subscription findSubscription = subscriptionService.findSubscriptionByChallengeAndUser(challengeNo, no);
        if(findSubscription == null){
            subscriptionService.saveSubscription(Subscription.setSubscription(challenge, user));
            List<Subscription> subscriptionList = subscriptionService.findSubscriptionByUser(no);
            List<SubscriptionDto> subscriptions = new ArrayList<>();
            if(!subscriptionList.isEmpty()){
                subscriptions = subscriptionList.stream()
                        .map(s -> new SubscriptionDto(s))
                        .collect(Collectors.toList());
            }
            return new Result(true, HttpStatus.OK.value(), subscriptions);
        }else{
            subscriptionService.deleteSubscription(findSubscription);
            List<Subscription> subscriptionList = subscriptionService.findSubscriptionByUser(no);
            List<SubscriptionDto> subscriptions = new ArrayList<>();
            if(!subscriptionList.isEmpty()){
                subscriptions = subscriptionList.stream()
                        .map(s -> new SubscriptionDto(s))
                        .collect(Collectors.toList());
            }
            return new Result(true, HttpStatus.OK.value(), subscriptions);
        }
    }

    @GetMapping("/challenge/{challengeNo}")
    public Result getChallenge(@PathVariable int challengeNo) {
        Challenge challenge = challengeService.findChallengeByChallengeNo(challengeNo);
        if(challenge == null) return new Result(false, HttpStatus.BAD_REQUEST.value());
        else {
            List<Challenger> challenger = challengeService.getChallengerByChallengeNo(challengeNo);
            List<UserDto> challengers = new ArrayList<>();
            if(!challenger.isEmpty()){
                challengers = challenger.stream()
                        .map(cs -> {
                            User user = userService.findByNo(cs.getUser().getNo());
                            return new UserDto(user);
                        }).collect(Collectors.toList());
            }
            ChallengeDto challengeResponse = new ChallengeDto(challenge, challengers);
            return new Result(true, HttpStatus.OK.value(), challengeResponse);
        }
    }

    /**
     *  구독 여부 확인
     * */
    @GetMapping("/challenge/{challengeNo}/subscribe/{userNo}")
    public Result isSubscription(@PathVariable int challengeNo, @PathVariable int userNo){
        Subscription subscription = null;

        subscription = subscriptionService.findSubscriptionByChallengeAndUser(challengeNo, userNo);

        if(subscription != null){
            return new Result(true, HttpStatus.OK.value());
        }
        return new Result(false, HttpStatus.OK.value());
    }


    /**
     * 구독 취소
     */
    @DeleteMapping("/challenge/{challengeNo}/subscribe/{no}")
    public Result removeSubscription(@PathVariable int challengeNo, @PathVariable int no) {
        Challenge challenge = challengeService.findChallengeByChallengeNo(challengeNo);
        if(challenge == null) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
        User user = userService.findByNo(no);

        subscriptionService.deleteSubscription(Subscription.setSubscription(challenge, user));
        return new Result(true, HttpStatus.OK.value());
    }

    /**
     * 구독 취소
     */
    @GetMapping("/subscribe/{userNo}")
    public Result getSubscription(@PathVariable int userNo) {
        List<Subscription> subscriptionByUser = subscriptionService.findSubscriptionByUser(userNo);
        List<SubscriptionDto> subscriptions = new ArrayList<>();
        if(!subscriptionByUser.isEmpty()){
            subscriptions = subscriptionByUser.stream()
                    .map(s -> new SubscriptionDto(s))
                    .collect(Collectors.toList());
        }
        return new Result(true, HttpStatus.OK.value(), subscriptions);
    }
}