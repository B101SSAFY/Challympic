package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {

    List<Alert> findAlertByUser_No(int userNo);
}
