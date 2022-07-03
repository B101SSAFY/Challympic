package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

    Optional<Follow> findBySrcUser_NoAndDestUser_No(int srcNo, int destNo);

    List<Follow> findBySrcUser_No(int userNo);

    List<Follow> findByDestUser_No(int userNo);

}
