package com.ssafy.challympic.domain;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략. AUTO_INCREMENT0
    @Column(name = "user_no")
    private int no;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String pwd;

    @Column(columnDefinition = "varchar(100) default 'USER'")
    @Enumerated(EnumType.STRING)
    private UserAuthEnum auth = UserAuthEnum.USER;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regdate;

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

    @OneToMany(mappedBy = "follow_following_no")
    private List<Follow> following;

    @OneToMany(mappedBy = "follow_follower_no")
    private List<Follow> follower;

    @OneToMany(mappedBy = "user")
    private List<QnA> qna;

    // 생성
    @Builder
    public User(String email, String pwd, String nickname) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
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

}

