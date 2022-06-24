package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Integer> {

    @Query("SELECT i FROM Interest i WHERE i.user.no = :userNo")
    List<Interest> findAllByUser(@Param("userNo") int userNo);

    @Query("SELECT i FROM Interest i WHERE i.user.no = :userNo AND i.tag.no = :tagNo")
    Optional<Interest> findByUserAndTag(@Param("userNo") int userNo, @Param("tagNo") int tagNo);

}
