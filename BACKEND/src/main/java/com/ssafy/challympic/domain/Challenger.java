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
public class Challenger {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenger_no")
    private int challenger_no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

    @Builder
    public Challenger(int challenger_no, User user, Challenge challenge) {
        this.challenger_no = challenger_no;
        this.user = user;
        this.challenge = challenge;
    }
}
