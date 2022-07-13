package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Alert;
import com.ssafy.challympic.domain.PostLike;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    private final PostService postService;

    private final UserService userService;

    private final AlertService alertService;

    public List<PostLike> getPostLikeListByPostNo(Integer postNo){
        return postLikeRepository.findByPost_No(postNo);
    }

    public boolean getPostLikeByUserNoPostNo(Integer postNo, Integer userNo){
        return postLikeRepository.findByPost_NoAndUser_No(postNo, userNo).isPresent();
    }

    @Transactional
    public void delete(Integer postNo, Integer userNo){
        PostLike postLike = postLikeRepository.findByPost_NoAndUser_No(postNo, userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트를 유저가 좋아요 누르지 않았습니다."));

        postLikeRepository.delete(postLike);
    }

    @Transactional
    public void save(PostLike postLike){
        postLikeRepository.save(postLike);

        // 좋아요를 눌렀을때 알림 설정
        User writer = postService.getPost(postLike.getPost().getNo()).getUser();
        Alert alert = Alert.builder()
                .user(writer)
                .content(userService.findByNo(postLike.getUser().getNo()).getNickname() + "님이 포스트에 좋아요를 눌렀습니다.")
                .build();
        alertService.saveAlert(alert);

    }

}
