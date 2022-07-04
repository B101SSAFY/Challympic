package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Integer> {

    @Query("select s from Search s where s.user.no = :userNo")
    List<Search> findByUserNo(int userNo);

}
