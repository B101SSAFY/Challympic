package com.ssafy.challympic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Title {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_no")
    private int no;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

    @Builder
    public Title(String name, User user, Challenge challenge) {
        this.name = name;
        this.user = user;
        this.challenge = challenge;
    }

    public Title update(Challenge challenge) {
        this.challenge = challenge;
        return this;
    }
}
