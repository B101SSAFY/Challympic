package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByPost_No(int postNo);

    List<Comment> findByUser_No(int userNo);

    int countByPost_No(int postNo);

}
