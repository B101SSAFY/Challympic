package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.User.FollowCnt;
import com.ssafy.challympic.api.Dto.User.FollowListResponse;
import com.ssafy.challympic.api.Dto.User.FollowRequest;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.AlertService;
import com.ssafy.challympic.service.FollowService;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

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
        return new Result(true, HttpStatus.OK.value(), new FollowCnt(followerCnt, followingCnt));
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


    /**
     * 프론트와 상의 후 변경해야 함
     * @param <T>
     */
    @Data
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