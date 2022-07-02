package com.ssafy.challympic.service;

import com.ssafy.challympic.api.CommentApiController;
import com.ssafy.challympic.api.Dto.Comment.CommentResponse;
import com.ssafy.challympic.api.Dto.Comment.CommentSaveRequest;
import com.ssafy.challympic.api.Dto.Comment.CommentUpdateRequest;
import com.ssafy.challympic.domain.Alert;
import com.ssafy.challympic.domain.Comment;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.CommentRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final PostService postService;

    private final AlertService alertService;

    @Transactional
    public CommentResponse save(CommentSaveRequest request){

        User findUser = userService.findByNo(request.getUser_no());
        Post findPost = postService.getPost(request.getPost_no());

        Comment newComment = Comment.builder()
                .user(findUser)
                .post(findPost)
                .content(request.getComment_content())
                .build();

        Comment comment = commentRepository.save(newComment);

        // 댓글 작성시 알람 설정
        User writer = findPost.getUser();   // 포스트 작성자
        Alert commentAlert = Alert.builder()
                .user(writer)
                .content(findUser.getNickname() + "님이 댓글을 작성했습니다.")
                .build();
        alertService.saveAlert(commentAlert);

        Alert tagAlert = null;
        String content = request.getComment_content();
        String[] splitSharp = content.split(" ");
        for(String str : splitSharp) {
            if(str.startsWith("@")) {
                User tagUser = userService.findByNickname(str.substring(1));    // 태그된 사람
                if(tagUser == null) continue;
                tagAlert = Alert.builder()
                        .user(tagUser)
                        .content(findUser.getNickname() + "님이 댓글에서 태그했습니다.")
                        .build();
                alertService.saveAlert(tagAlert);
            }
        }

        return new CommentResponse(comment);
    }

    public List<Comment> findByPost(int post_no){
        return commentRepository.findByPost_No(post_no);
    }

//    public int findCommentLikeCnt(int )

    public Comment findOne(int comment_no){
        return commentRepository.findById(comment_no)
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
    }

    @Transactional
    public void update(CommentUpdateRequest request){

        Comment comment = commentRepository.findById(request.getComment_no())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        Comment updateComment = comment.update(request.getComment_content());
    }

    @Transactional
    public void delete(int comment_no){
        Comment comment = commentRepository.findById(comment_no)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        commentRepository.delete(comment);
    }

    @Transactional
    public void report(int comment_no) {
        Comment comment = commentRepository.findById(comment_no)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.updateReport();
    }

    public int commentReportCntByUser(int user_no){
        List<Comment> findCommentList = commentRepository.findByUser_No(user_no);
        int reportCnt = 0;
        for (Comment comment : findCommentList) {
            reportCnt += comment.getReport();
        }
        return reportCnt;
    }

    public int postCommentCnt(int post_no) {
        return commentRepository.findByPost_No(post_no).size();
    }
}
