package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Comment.*;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.*;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final UserService userService;
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
            return getResult(request.getPost_no());
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @DeleteMapping("/comment")
    public Result delete(@RequestBody CommentDeleteRequest request){
        try {
            commentService.delete(request.getComment_no());
            return getResult(request.getPost_no());
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * 프론트 수정 필요!
     * 업데이트, 삭제시 리스트 전체를 반환할 필요는 없어보임.
     */
    private Result getResult(int post) {
        List<Comment> commentList = commentService.findByPost(post);
        List<CommentDto> comments = new ArrayList<>();
        if(!commentList.isEmpty()){
            comments = commentList.stream()
                    .map(c -> new CommentDto(c))
                    .collect(Collectors.toList());
        }
        return new Result(true, HttpStatus.OK.value(), comments);
    }

    @PostMapping("/comment/like")
    public Result like(@RequestBody CommentRequest request){
        CommentLike commentLike = commentLikeService.findOne(request.user_no, request.comment_no);
        if(commentLike != null){
            commentLikeService.delete(request.user_no, request.comment_no);
            return new Result(true, HttpStatus.OK.value(), null, false);
        }else{
            User user = userService.findByNo(request.user_no);
            Comment comment = commentService.findOne(request.comment_no);
            if(user == null || comment == null){
                return new Result(true, HttpStatus.OK.value(), "유저나 코멘트가 이상", true);
            }
            commentLike = new CommentLike();
            commentLike.setUser(user);
            commentLike.setComment(comment);
            commentLikeService.save(commentLike);
            return new Result(true, HttpStatus.OK.value(), null, true);
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

    @Data
    static class CommentDto{
        private int comment_no;
        private String comment_content;
        private int like_cnt;
        private String comment_regdate;
        private int comment_report;
        private int user_no;
        private String user_nickname;
        private String user_profile;

        public CommentDto(Comment comment) {
            this.comment_no = comment.getNo();
            this.comment_content = comment.getContent();
            this.comment_report = comment.getReport();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            this.comment_regdate = formatter.format(comment.getRegdate());
            this.user_no = comment.getUser().getNo();
            this.user_nickname = comment.getUser().getNickname();
            if(comment.getUser().getMedia() != null){
                this.user_profile = comment.getUser().getMedia().getFile_path()+File.separator+comment.getUser().getMedia().getFile_savedname();
            }
        }
    }

    @Data
    static class CommentRequest{
        private int comment_no;
        private int user_no;
        private int post_no;
        private String comment_content;
    }

    /**
     * data안에 isLiked 넣을 수 있을 듯
     * 프론트와 추가 수정 필요
     * @param <T>
     */
    @Data
    @AllArgsConstructor
    static class Result<T>{
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
