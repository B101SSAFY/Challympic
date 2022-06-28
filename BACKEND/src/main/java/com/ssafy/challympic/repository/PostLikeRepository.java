package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {

    List<PostLike> findByPost_No(int postNo);

    int countByPost_No(int postNo);

    List<PostLike> findByUser_No(int userNo);

    Optional<PostLike> findByPost_NoAndUser_No(int postNo, int userNo);

}
