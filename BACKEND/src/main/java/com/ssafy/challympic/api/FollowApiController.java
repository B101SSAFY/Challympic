package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.User.FollowCntResponse;
import com.ssafy.challympic.api.Dto.User.FollowListResponse;
import com.ssafy.challympic.api.Dto.User.FollowRequest;
import com.ssafy.challympic.service.FollowService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("/user/{userNo}/follow")
    public Result follow(@PathVariable("userNo") int user_no, @RequestBody FollowRequest request){
        try {
            boolean isFollow = followService.follow(user_no, request.getFollow_follower_no());
            return new Result(true, HttpStatus.OK.value(), null, isFollow);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @GetMapping("/{userNo}/follow/{followerNo}")
    public Result isFollow(@PathVariable("userNo") int srcNo, @PathVariable("followerNo") int destNo){
        if(srcNo == destNo){
            return new Result(true, HttpStatus.OK.value(), null, false);
        }
        boolean follow = followService.isFollow(srcNo, destNo);
        return new Result(true, HttpStatus.OK.value(), null, follow);
    }

    @GetMapping("/{userNo}/follow")
    public Result followCnt(@PathVariable("userNo") int user_no){
        int followerCnt = followService.followingCnt(user_no); // 피드 주인을 팔로우한
        int followingCnt = followService.followerCnt(user_no); // 피드 주인이 팔로우한
        return new Result(true, HttpStatus.OK.value(), new FollowCntResponse(followerCnt, followingCnt));
    }

    @GetMapping("/{userNo}/follower/{loginUser}")
    public Result follower(@PathVariable("userNo") int userNo, @PathVariable("loginUser") int loginUser){
        List<FollowListResponse> followerList = followService.following(userNo, loginUser);
        return new Result(true, HttpStatus.OK.value(), followerList);
    }

    @GetMapping("/{userNo}/following/{loginUser}")
    public Result following(@PathVariable("userNo") int user_no, @PathVariable("loginUser") int login_user){
        List<FollowListResponse> followingList = followService.follower(user_no, login_user);
        return new Result(true, HttpStatus.OK.value(), followingList);
    }


    @Data // isFollowing과 cnt data 안에 넣을 수 있는지 프론트와 상의
    @AllArgsConstructor
    static class Result<T>{
        private boolean isSuccess;
        private int code;
        private T data;
        private boolean isFollowing;
        private int cnt;

        public Result(boolean isSuccess, int code) {
            this.isSuccess = isSuccess;
            this.code = code;
        }

        public Result(boolean isSuccess, int code, T data) {
            this.isSuccess = isSuccess;
            this.code = code;
            this.data = data;
        }

        public Result(boolean isSuccess, int code, T data, boolean isFollowing) {
            this.isSuccess = isSuccess;
            this.code = code;
            this.data = data;
            this.isFollowing = isFollowing;
        }
    }
}