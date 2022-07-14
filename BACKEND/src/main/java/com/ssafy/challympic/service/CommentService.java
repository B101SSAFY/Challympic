package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Comment.CommentListResponse;
import com.ssafy.challympic.api.Dto.Comment.CommentResponse;
import com.ssafy.challympic.api.Dto.Comment.CommentSaveRequest;
import com.ssafy.challympic.api.Dto.Comment.CommentUpdateRequest;
import com.ssafy.challympic.domain.Alert;
import com.ssafy.challympic.domain.Comment;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final AlertRepository alertRepository;

    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentResponse save(CommentSaveRequest request){

        User user = userRepository.findById(request.getUser_no())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        Post post = postRepository.findById(request.getPost_no())
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다."));

        Comment newComment = Comment.builder()
                .user(user)
                .post(post)
                .content(request.getComment_content())
                .build();

        Comment comment = commentRepository.save(newComment);

        // 댓글 작성시 알람 설정
        User writer = post.getUser();   // 포스트 작성자
        Alert commentAlert = Alert.builder()
                .user(writer)
                .content(user.getNickname() + "님이 댓글을 작성했습니다.")
                .build();
        alertRepository.save(commentAlert);

        Alert tagAlert = null;
        String content = request.getComment_content();
        String[] splitSharp = content.split(" ");
        for(String str : splitSharp) {
            if(str.startsWith("@")) {
                User tagUser = userRepository.findByNickname(str.substring(1))
                        .orElseThrow(() -> new IllegalArgumentException("해당 닉네임이 없습니다."));    // 태그된 사람
                if(tagUser == null) continue;
                tagAlert = Alert.builder()
                        .user(tagUser)
                        .content(user.getNickname() + "님이 댓글에서 태그했습니다.")
                        .build();
                alertRepository.save(tagAlert);
            }
        }

        return new CommentResponse(comment);
    }

    public List<CommentListResponse> findByPost(int postNo, int userNo){
        List<Comment> commentList = commentRepository.findByPost_No(postNo);
        return commentList.stream()
                .map(c -> {
                    boolean IsLiked = commentLikeRepository.findByUser_NoAndComment_No(userNo, c.getNo()).isPresent();
                    return new CommentListResponse(c, IsLiked);
                })
               .collect(Collectors.toList());
    }

    @Transactional
    public void update(CommentUpdateRequest request){

        Comment comment = commentRepository.findById(request.getComment_no())
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        comment.update(request.getComment_content());
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
