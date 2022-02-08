package com.ssafy.challympic;

import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.UserAuthEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;
    @PostConstruct
    public void init(){
        initService.dbInit();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{

        private final EntityManager em;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        public void dbInit(){
            User user = new User();
            user.setUser_email("admin@ssafy.com");
            String rawPwd = "123";
            String encPwd = bCryptPasswordEncoder.encode(rawPwd);
            user.setUser_pwd(encPwd);
            user.setUser_nickname("관리자");
            user.setUser_auth(UserAuthEnum.ADMIN);

            em.persist(user);
        }
    }
}