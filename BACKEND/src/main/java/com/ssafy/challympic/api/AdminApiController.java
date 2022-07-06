package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Challenge.ChallengeRequest;
import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.Comment.CommentDeleteRequest;
import com.ssafy.challympic.api.Dto.QnA.QnADto;
import com.ssafy.challympic.api.Dto.QnA.QnARequest;
import com.ssafy.challympic.api.Dto.User.UserRequest;
import com.ssafy.challympic.domain.Comment;
import com.ssafy.challympic.domain.QnA;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.UserActive;
import com.ssafy.challympic.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;
    private final CommentLikeService commentLikeService;
    private final CommentService commentService;
    private final PostService postService;
    private final ChallengeService challengeService;
    private final QnAService qnaService;
    private final SubscriptionService subscriptionService;

    @GetMapping("/admin/users")
    public Result userList(){
        List<User> userList = adminService.userList();
        List<UserDto> collect = userList.stream()
                .map(u -> {
                    UserDto userDto = new UserDto(u);
                    int post_report = postService.postReportCntByUser(u.getNo());
                    int challenge_report = challengeService.challengeReportCntByUser(u.getNo());
                    int comment_report = commentService.commentReportCntByUser(u.getNo());
                    userDto.setReport(post_report+challenge_report+comment_report);
                    return userDto;
                })
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @PutMapping("/admin/users")
    public Result inactiveUser(@RequestBody UserRequest userRequest){
        adminService.updateUserActive(userRequest.getUser_no());
        return new Result(true, HttpStatus.OK.value());
    }

    @DeleteMapping("/admin/users")
    public Result deleteUser(@RequestBody UserRequest userRequest){
        adminService.deleteUser(userRequest.getUser_no());
        return new Result(true, HttpStatus.OK.value());
    }

    @GetMapping("/admin/challenges")
    public Result challengeList(){
        List<ChallengeDto> challengeList = adminService.challengeList();
        return new Result(true, HttpStatus.OK.value(), challengeList);
    }

    @DeleteMapping("/admin/challenges")
    public Result deleteChallenge(@RequestBody ChallengeRequest challengeRequest){
        // 하위 포스트 삭제
        adminService.deletePostByChallenge(challengeRequest.getChallenge_no());
        // 챌린지 삭제
        adminService.deleteChallenge(challengeRequest.getChallenge_no());
        return new Result(true, HttpStatus.OK.value());
    }

    @GetMapping("/admin/comments")
    public Result commentList(){
        List<Comment> commentList = adminService.commentList();
        List<CommentDto> collect = commentList.stream()
                .map(c -> {
                    CommentDto commentDto = new CommentDto(c);
                    int like_cnt = commentLikeService.findLikeCntByComment(c.getNo());
                    commentDto.setLike_cnt(like_cnt);
                    return commentDto;
                }).collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @DeleteMapping("/admin/comments")
    public Result deleteComment(@RequestBody CommentDeleteRequest commentRequest){
        adminService.deleteComment(commentRequest.getComment_no());
        return new Result(true, HttpStatus.OK.value());
    }

    @GetMapping("/admin/qna")
    public Result qnaList(){
        List<QnADto> qnaList = adminService.qnaList();
        return new Result(true, HttpStatus.OK.value(), qnaList);
    }

    @PutMapping("/admin/qna")
    public Result answer(@RequestBody QnARequest qnaRequest){
        QnA findOne;
        try{
            adminService.updateQnA(qnaRequest.getQna_no(), qnaRequest.getQna_answer());
            findOne = qnaService.findOne(qnaRequest.getQna_no());
        } catch (NoSuchElementException e) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }

        if(findOne.getAnswer() == null) return new Result(false, HttpStatus.BAD_REQUEST.value());
        else return new Result(true, HttpStatus.OK.value());
    }


    @Data
    static class CommentDto{
        private int comment_no;
        private String comment_content;
        private int like_cnt;
        private LocalDateTime comment_regdate;
        private int comment_report;

        public CommentDto(Comment comment) {
            this.comment_no = comment.getNo();
            this.comment_content = comment.getContent();
            this.comment_regdate = comment.getCreatedDate();
            this.comment_report = comment.getReport();
        }
    }

    @Data
    static class UserDto{
        private int user_no;
        private String user_email;
        private String user_nickname;
        private UserActive user_active;
        private int report;

        public UserDto(User user) {
            this.user_no = user.getNo();
            this.user_email = user.getEmail();
            this.user_nickname = user.getNickname();
            this.user_active = user.getActive();
        }
    }
}
