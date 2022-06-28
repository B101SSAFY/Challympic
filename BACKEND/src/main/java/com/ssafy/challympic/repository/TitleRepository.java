package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<Title, Integer> {

    Title findByChallenge_No(int challengeNo);

    List<Title> findByUser_No(int userNo);
}
