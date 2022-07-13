package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.BaseTimeEntity;
import com.ssafy.challympic.domain.defaults.UserActive;
import com.ssafy.challympic.domain.defaults.UserAuthEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Table(uniqueConstraints = {@UniqueConstraint(name = "email_nickname_unique", columnNames = {"email", "nickname"})})
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략. AUTO_INCREMENT0
    private int no;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String pwd;

    @Column(columnDefinition = "varchar(100) default 'USER'")
    @Enumerated(EnumType.STRING)
    private UserAuthEnum auth = UserAuthEnum.USER;

    @Column(columnDefinition = "varchar(100) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private UserActive active = UserActive.ACTIVE;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inactivedate;

    @Column(nullable = false)
    private String nickname;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "file_no")
    private Media media;

    @Column(columnDefinition = "varchar(50) default '도전자'")
    private String title;

    @OneToMany(mappedBy = "user")
    private List<Challenge> challenge;

    @OneToMany(mappedBy = "user")
    private List<Interest> interest;

    @OneToMany(mappedBy = "user")
    private List<Subscription> subscription;

    @OneToMany(mappedBy = "user")
    private List<QnA> qna;

    // 생성
    @Builder
    public User(String email, String pwd, String nickname) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.title = "도전자";
    }

    // 수정
    public User update(String nickname, Media file, String title){
        this.nickname = nickname;
        this.media = file;
        this.title = title;
        return this;
    }

    public User updatePwd(String pwd){
        this.pwd = pwd;
        return this;
    }

    public User updateActive(){
        if (this.active == UserActive.ACTIVE)
            this.active = UserActive.INACTIVE;
        else this.active = UserActive.ACTIVE;
        return this;
    }

}

