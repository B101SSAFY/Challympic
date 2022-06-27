package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void join(){
        //given
        String email = "test";
        String nickname = "test";
        String pwd = "test";

        userRepository.save(User.builder()
                .email(email)
                .nickname(nickname)
                .pwd(pwd).build());

        //when
        List<User> userList = userRepository.findAll();

        User user = userList.get(0);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getNickname()).isEqualTo(nickname);
    }

}