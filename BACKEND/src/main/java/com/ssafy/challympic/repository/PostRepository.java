package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

//    @Query("SELECT p FROM Post p WHERE p.challenge.no = :challengeNo")
    List<Post> findByChallengeNo(int challengeNo);

    List<Post> findTop50ByOrderByNoDesc();

    List<Post> findByUserNo(int userNo);

    @Query("SELECT p FROM Post p WHERE p.no = (SELECT pt.post.no FROM PostTag pt WHERE pt.tag.content = :tagContent)")
    List<Post> findByTag(@Param("tagContent") String tagContent);

}
