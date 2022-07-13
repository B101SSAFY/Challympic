package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.User.*;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.*;
import com.ssafy.challympic.util.S3Uploader;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final TitleService titleService;
    private final PostService postService;

    @GetMapping("/user/account/{userNo}")
    public Result findUser(@PathVariable("userNo") int userNo){
        try {
            return getResult(userNo, userNo);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("/join")
    public Result join(@RequestBody UserJoinRequest request){
        int join_no = userService.join(request); // 바로 반환할 수 있는데 수정하면 좋겠다.. front

        return new Result(true, HttpStatus.OK.value());
    }

    /**
     * 파일 수정 추가 필요
     */
    @PutMapping("/user/account/{userNo}")
    public Result updateUser(@PathVariable("userNo") int no, UserUpdateRequest request){
        try{
            int returnNo = userService.updateUser(no, request);
            return getResult(no, returnNo);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    private Result getResult(@PathVariable("userNo") int no, int returnNo) {
        User user = userService.findByNo(returnNo);
        List<Title> titles = titleService.findTitlesByUserNo(no);
        List<Interest> interests = user.getInterest();
        List<Subscription> subscriptions = user.getSubscription();
        return new Result(true, HttpStatus.OK.value(), new UserResponse(user, titles, interests, subscriptions));
    }

    @PutMapping("/user/account/{userNo}/pwd")
    public Result updatePwd(@PathVariable("userNo") int no, @RequestBody UserUpdatePwdRequest request){
        try{
            int returnNo = userService.updatePwd(no, request);
            User byNo = userService.findByNo(returnNo);
            return new Result(true, HttpStatus.OK.value(), new UserResponse(byNo));
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @DeleteMapping("/user/account/{userNo}")
    public Result deleteUser(@PathVariable("userNo") int user_no){
        userService.deleteUser(user_no);
        return new Result(true, HttpStatus.OK.value());
    }

    @PostMapping("/confirm/email")
    public Result confirmEmail(@RequestBody UserConfirmRequest request){
        boolean result = userService.validateDuplicateEmail(request.getUser_email());
        return new Result(true, HttpStatus.OK.value(), result);
    }

    @PostMapping("/confirm/nickname")
    public Result confirmNickname(@RequestBody UserConfirmRequest request){
        boolean result = userService.validateDuplicateNickname(request.getUser_nickname());

        return new Result(true, HttpStatus.OK.value(), result);
    }

    // TODO : 왜 Kings인가요?
    static class Kings implements Comparable<Kings> {
        private int user_no;
        private int post_cnt;

        public Kings(int user_no, int post_cnt) {
            this.user_no = user_no;
            this.post_cnt = post_cnt;
        }

        @Override
        public int compareTo(Kings o) {
            return o.post_cnt - this.post_cnt;
        }
    }

    /**
     * 이달의 도전왕
     */
    @GetMapping("/champions")
    public Result getChampions(){
        List<User> allUser = userService.findAllUser();
        List<Kings> kings = new ArrayList<>();
        for (User user : allUser) {
            if(user.getNo() != 1){
                kings.add(new Kings(user.getNo(), postService.getPostListByUserNo(user.getNo()).size() ));
            }
        }

        Collections.sort(kings);

        List<UserResponse> userList = new ArrayList<>();
        if(!kings.isEmpty()){
            userList = kings.stream()
                    .map(k -> {
                        int no = k.user_no;
                        User user = userService.findByNo(no);
                        return new UserResponse(user);
                    })
                    .collect(Collectors.toList());
        }
        if(userList.size() < 5){
            return new Result(true, HttpStatus.OK.value(), userList);
        }else {
            return new Result(true, HttpStatus.OK.value(), userList.subList(0,5));
        }
    }

}