package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepository extends JpaRepository<QnA, Integer> {

    List<QnA> findByUser_No(int user_no);
}
