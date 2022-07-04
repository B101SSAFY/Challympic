package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Integer> {

    Optional<CommentLike> findByUser_NoAndComment_No(int userNo, int commentNo);

    List<CommentLike> findByComment_No(int commentNo);

}
