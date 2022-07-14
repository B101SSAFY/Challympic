package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Challenge.ChallengeAdminListResponse;
import com.ssafy.challympic.api.Dto.Challenge.ChallengeRequest;
import com.ssafy.challympic.api.Dto.Comment.CommentAdminListResponse;
import com.ssafy.challympic.api.Dto.Comment.CommentDeleteRequest;
import com.ssafy.challympic.api.Dto.QnA.QnAAdminListResponse;
import com.ssafy.challympic.api.Dto.QnA.QnARequest;
import com.ssafy.challympic.api.Dto.User.UserListResponse;
import com.ssafy.challympic.api.Dto.User.UserRequest;
import com.ssafy.challympic.domain.QnA;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;
    // TODO : 안쓰는 선언 삭제
    private final QnAService qnaService;

    @GetMapping("/admin/users")
    public Result userList(){
        List<UserListResponse> userList = adminService.userList();
        return new Result(true, HttpStatus.OK.value(), userList);
    }

    @PutMapping("/admin/users")
    public Result inactiveUser(@RequestBody UserRequest userRequest){
        try {
            adminService.updateUserActive(userRequest.getUser_no());
            return new Result(true, HttpStatus.OK.value());
        }catch (Exception e) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @DeleteMapping("/admin/users")
    public Result deleteUser(@RequestBody UserRequest userRequest){
        try {
            adminService.deleteUser(userRequest.getUser_no());
            return new Result(true, HttpStatus.OK.value());
        }catch (Exception e) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @GetMapping("/admin/challenges")
    public Result challengeList(){
        List<ChallengeAdminListResponse> challengeList = adminService.challengeList();
        return new Result(true, HttpStatus.OK.value(), challengeList);
    }

    @DeleteMapping("/admin/challenges")
    public Result deleteChallenge(@RequestBody ChallengeRequest challengeRequest){
        try{
            // 하위 포스트 삭제
            adminService.deletePostByChallenge(challengeRequest.getChallenge_no());
            // 챌린지 삭제
            adminService.deleteChallenge(challengeRequest.getChallenge_no());
            return new Result(true, HttpStatus.OK.value());
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @GetMapping("/admin/comments")
    public Result commentList(){
        List<CommentAdminListResponse> commentList = adminService.commentList();
        return new Result(true, HttpStatus.OK.value(), commentList);
    }

    @DeleteMapping("/admin/comments")
    public Result deleteComment(@RequestBody CommentDeleteRequest commentRequest){
        try {
            adminService.deleteComment(commentRequest.getComment_no());
            return new Result(true, HttpStatus.OK.value());
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @GetMapping("/admin/qna")
    public Result qnaList(){
        List<QnAAdminListResponse> qnaList = adminService.qnaList();
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

}
