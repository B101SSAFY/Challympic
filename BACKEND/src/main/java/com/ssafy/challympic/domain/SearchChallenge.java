package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class SearchChallenge extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Builder
    public SearchChallenge(Challenge challenge, User user) {
        this.challenge = challenge;
        this.user = user;
    }
}
