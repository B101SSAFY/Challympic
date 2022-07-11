package com.ssafy.challympic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Challenger {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int challenger_no; // TODO : 어째서.. 이름을 안바꿨을까요...?

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

    @Builder
    public Challenger(User user, Challenge challenge) {
        this.user = user;
        this.challenge = challenge;
    }
}
