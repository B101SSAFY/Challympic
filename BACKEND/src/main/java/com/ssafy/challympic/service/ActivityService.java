package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Activity;
import com.ssafy.challympic.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
// TODO : Transactional(readOnly = true) 추가해야함.
public class ActivityService {

    private final ActivityRepository activityRepository;

    // TODO: save가 있는데 왜 Transactional 안붙임
    public void saveActivity(Activity activity) {
        activityRepository.save(activity);
    }

    // TODO : 사용 안하는 메서드같은데
    public List<Activity> findActivityListByUserNo(int userNo) {
        return activityRepository.findAllByUser_no(userNo);
    }
}
