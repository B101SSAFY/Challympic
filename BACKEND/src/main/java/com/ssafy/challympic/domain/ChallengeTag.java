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
public class ChallengeTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_tag_no")
    private int challenge_tag_no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_no")
    private Tag tag;

    @Builder
    public ChallengeTag(int challenge_tag_no, Challenge challenge, Tag tag) {
        this.challenge_tag_no = challenge_tag_no;
        this.challenge = challenge;
        this.tag = tag;
    }
}
