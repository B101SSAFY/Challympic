package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Integer> {

    List<Search> findAllByUserNo(int userNo);

}
