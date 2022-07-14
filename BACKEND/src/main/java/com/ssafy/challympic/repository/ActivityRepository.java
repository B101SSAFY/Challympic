package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

}
