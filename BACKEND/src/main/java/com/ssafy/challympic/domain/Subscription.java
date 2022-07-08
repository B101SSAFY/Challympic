package com.ssafy.challympic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Subscription {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @ManyToOne
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @Builder
    public Subscription(Challenge challenge, User user) {
        this.challenge = challenge;
        this.user = user;
    }
}
