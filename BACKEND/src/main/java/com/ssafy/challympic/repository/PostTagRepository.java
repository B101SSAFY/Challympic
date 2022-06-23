package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Integer> {

    @Query("select pt from PostTag pt where pt.post.post_no = :postNo")
    List<PostTag> findAllByPostNo(@Param("postNo") int postNo);
}
