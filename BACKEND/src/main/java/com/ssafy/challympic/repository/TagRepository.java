package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByContent(String content);

    @Query("select t from Tag t where t.no = " +
            "(select pt.tag.no from PostTag pt where pt.post.no = :postNo)")
    List<Tag> findAllByPostNo(@Param("postNo") int postNo);

    @Query("select t from Tag t order by t.no DESC")
    List<Tag> findAllOrderByNoDesc();
}
