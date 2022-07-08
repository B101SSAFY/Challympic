package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Integer> {

    List<PostTag> findAllByPost_tag_no(@Param("postNo") int postNo);

}
