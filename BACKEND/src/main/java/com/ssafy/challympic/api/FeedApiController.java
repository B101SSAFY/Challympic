package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Feed.FeedChallengeListResponse;
import com.ssafy.challympic.api.Dto.Feed.FeedPostListResponse;
import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import com.ssafy.challympic.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FeedApiController {

    private final UserService userService;
    private final FeedService feedService;

    /**
     * 내가 만든 챌린지 목록
     * @param userNo
     * @return
     */
    @GetMapping("/feed/{userNo}/challenge")
    public Result getChallengeList(@PathVariable int userNo) {
        try{
            List<FeedChallengeListResponse> responses = feedService.getChallengeByUserNo(userNo);
            return new Result(true, HttpStatus.OK.value(), responses);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * 구독한 챌린지 목록
     * @param userNo
     * @return
     */
    @GetMapping("/feed/{userNo}/subscription")
    public Result getSubscriptionChallengeList(@PathVariable int userNo) {
        try {
            List<FeedChallengeListResponse> responses = feedService.getChallengeBySubscription(userNo);
            return new Result(true, HttpStatus.OK.value(), responses);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * 내가 좋아요한 포스트 목록
     */
    @GetMapping("/feed/{userNo}/like")
    public Result getLikePostList(@PathVariable int userNo) {
        try {
            List<FeedPostListResponse> responses = feedService.getLikePostListByUserNo(userNo);
            return new Result(true, HttpStatus.OK.value(), responses);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * 내가 만든 포스트 목록
     */
    @GetMapping("/feed/{userNo}/post")
    public Result postByUser(@PathVariable("userNo") int user_no){
        try {
            List<FeedPostListResponse> responses = feedService.getPostListByUserNo(user_no);
            return new Result(true, HttpStatus.OK.value(), responses);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    // TODO: 추후 추가 수정
    @GetMapping("/feed/{userNo}")
    public Result getFeedUserInfo(@PathVariable("userNo") int user_no){
        if(user_no == 0){
            return new Result(false, HttpStatus.NO_CONTENT.value());
        }
        User findUser = userService.findByNo(user_no);
        if(findUser == null){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }else{
            return new Result(true, HttpStatus.OK.value(), new UserDto(findUser));
        }
    }

    @Data // TODO : dto로
    static class UserDto{
        private int user_no;
        private String user_nickname;
        private String user_title;
        private int file_no;
        private String file_path;
        private String file_savedname;

        public UserDto(User user) {
            this.user_no = user.getNo();
            this.user_nickname = user.getNickname();
            this.user_title = user.getTitle();
            if(user.getMedia() !=  null){
                this.file_no = user.getMedia().getNo();
                this.file_path = user.getMedia().getFile_path();
                this.file_savedname = user.getMedia().getFile_savedname();
            }
        }
    }
}
