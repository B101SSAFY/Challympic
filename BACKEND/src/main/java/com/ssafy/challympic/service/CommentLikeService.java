package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Comment.CommentLikeRequest;
import com.ssafy.challympic.domain.Comment;
import com.ssafy.challympic.domain.CommentLike;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.CommentLikeRepository;
import com.ssafy.challympic.repository.CommentRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public int save(CommentLike commentLike){
        return commentLikeRepository.save(commentLike).getNo();
    }

    public boolean findCommentLike(CommentLikeRequest request){
        try{
            CommentLike commentLike = commentLikeRepository.findByUser_NoAndComment_No(request.getUser_no(), request.getComment_no()).get();
            commentLikeRepository.delete(commentLike);
            return false;
        }catch (Exception e){
            User user = userRepository.findById(request.getUser_no())
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
            Comment comment = commentRepository.findById(request.getComment_no())
                    .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

            CommentLike newCommentLike = CommentLike.builder()
                    .user(user)
                    .comment(comment)
                    .build();
            commentLikeRepository.save(newCommentLike);
            return true;
        }
    }


    public int findLikeCntByComment(int commentNo){
        return commentLikeRepository.findByComment_No(commentNo).size();
    }

    public boolean findIsLikeByUser(int userNo,int commentNo) {
        try {
            commentLikeRepository.findByUser_NoAndComment_No(userNo, commentNo).get();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}


