package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    @Query("select t.user from Title t where t.user.no = (select count(t) from Title t group by t.user.no)")
    List<User> findRank();

}