package com.ssafy.challympic.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class SearchChallenge {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_challenge_no")
    private int search_challenge_no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date search_regdate;

    @Builder
    public SearchChallenge(int search_challenge_no, Challenge challenge, User user, Date search_regdate) {
        this.search_challenge_no = search_challenge_no;
        this.challenge = challenge;
        this.user = user;
        this.search_regdate = search_regdate;
    }
}
