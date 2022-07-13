package com.ssafy.challympic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class ChallengeTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no; // TODO : 이것도... 안바꿨군요...

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_no")
    private Tag tag;

    @Builder
    public ChallengeTag(Challenge challenge, Tag tag) {
        this.challenge = challenge;
        this.tag = tag;
    }
}
