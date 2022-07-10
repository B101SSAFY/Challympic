package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findAllByUser_no(int userNo);

}
