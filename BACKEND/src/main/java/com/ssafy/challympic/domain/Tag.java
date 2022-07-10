package com.ssafy.challympic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    private String content;

    private String isChallenge;

    @JsonIgnore
    @OneToMany(mappedBy = "tag", fetch = LAZY)
    private List<Interest> interest;

    @Builder
    public Tag(String content, String isChallenge, List<Interest> interest) {
        this.content = content;
        this.isChallenge = isChallenge;
        this.interest = interest;
    }
}
