package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findAllByUser_no(int userNo); // TODO : 호출 잘 되는지 확인 필요요

}
