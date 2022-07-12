package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Comment.*;
import com.ssafy.challympic.service.CommentLikeService;
import com.ssafy.challympic.service.CommentService;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    @PostMapping("/comment")
    public Result save(@RequestBody CommentSaveRequest request){
        try{
            CommentResponse comment = commentService.save(request);
            return new Result(true, HttpStatus.OK.value(), comment);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @PutMapping("/comment")
    public Result update(@RequestBody CommentUpdateRequest request){
        try {
            commentService.update(request);
            return getResult(request.getPost_no(), request.getUser_no());
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @DeleteMapping("/comment")
    public Result delete(@RequestBody CommentDeleteRequest request){
        try {
            commentService.delete(request.getComment_no());
            return getResult(request.getPost_no(), request.getUser_no());
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * 프론트 수정 필요!
     * 업데이트, 삭제시 리스트 전체를 반환할 필요는 없어보임.
     * 시간 반환 regdate 수정 필요!
     */
    private Result getResult(int postNo, int userNo) {
        List<CommentListResponse> commentList = commentService.findByPost(postNo, userNo);
        return new Result(true, HttpStatus.OK.value(), commentList);
    }

    @PostMapping("/comment/like")
    public Result like(@RequestBody CommentLikeRequest request){
        try {
            boolean commentLike = commentLikeService.findCommentLike(request);
            return new Result(true, HttpStatus.OK.value(), null, commentLike);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @PutMapping("/report/comment")
    public Result report(@RequestBody CommentReportRequest request){
        try {
            commentService.report(request.getComment_no());
            return new Result(true, HttpStatus.OK.value());
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * data안에 isLiked 넣을 수 있을 듯
     * 프론트와 추가 수정 필요
     * @param <T>
     */
    @Data
    @AllArgsConstructor
    static class Result<T>{ // TODO: Dto로 이동?
        private boolean isSuccess;
        private int code;
        private T data;
        private boolean isLiked;

        public Result(boolean isSuccess, int code) {
            this.isSuccess = isSuccess;
            this.code = code;
        }

        public Result(boolean isSuccess, int code, T data) {
            this.isSuccess = isSuccess;
            this.code = code;
            this.data = data;
        }
    }

}
