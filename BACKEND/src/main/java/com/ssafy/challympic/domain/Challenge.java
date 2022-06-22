package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Challenge {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_no")
    private int challenge_no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date challenge_start;

    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date challenge_end;

    @Column(columnDefinition = "varchar(10) default 'PUBLIC'")
    @Enumerated(EnumType.STRING)
    private ChallengeAccess challenge_access;

    private ChallengeType challenge_type;

    @Column(nullable = false)
    private String challenge_title;

    @Column(nullable = false)
    private String challenge_content;

    @Column(nullable = true)
    private boolean challenge_official;

    @ColumnDefault("0")
    private int challenge_report;

    @Builder
    public Challenge(int challenge_no, User user, Date challenge_start, Date challenge_end, ChallengeAccess challenge_access, ChallengeType challenge_type, String challenge_title, String challenge_content, boolean challenge_official, int challenge_report) {
        this.challenge_no = challenge_no;
        this.user = user;
        this.challenge_start = challenge_start;
        this.challenge_end = challenge_end;
        this.challenge_access = challenge_access;
        this.challenge_type = challenge_type;
        this.challenge_title = challenge_title;
        this.challenge_content = challenge_content;
        this.challenge_official = challenge_official;
        this.challenge_report = challenge_report;
    }
}
