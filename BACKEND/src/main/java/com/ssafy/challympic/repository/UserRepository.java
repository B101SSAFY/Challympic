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


    @Query("SELECT u FROM User u WHERE u.no = :email AND u.pwd = :pwd")
    User login(String email, String pwd);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    @Query("SELECT u FROM User u")
    List<User> findAllUser();
}