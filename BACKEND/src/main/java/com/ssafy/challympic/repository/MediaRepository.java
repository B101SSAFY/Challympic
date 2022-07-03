package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Media;
import com.ssafy.challympic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

public interface MediaRepository extends JpaRepository<Media, Integer> {

}
