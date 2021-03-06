package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Activity.ActivityRequest;
import com.ssafy.challympic.domain.Activity;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.ActivityRepository;
import com.ssafy.challympic.repository.PostRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public void saveActivity(ActivityRequest request) {
        Post post = postRepository.findById(request.getPost_no())
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다."));
        User user = userRepository.findById(request.getUser_no())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        Activity activity = Activity.builder()
                .post(post)
                .user(user)
                .build();
        activityRepository.save(activity);
    }

}

