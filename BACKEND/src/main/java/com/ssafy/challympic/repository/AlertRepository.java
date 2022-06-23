package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {

    @Query("select a from Alert a where a.user.no = :userNo")
    List<Alert> findAlertByUserNo(@Param("userNo") int userNo);
}
