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
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @Column(columnDefinition = "varchar(10) default 'PUBLIC'")
    @Enumerated(EnumType.STRING)
    private ChallengeAccess access;

    private ChallengeType type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private boolean official;

    @ColumnDefault("0")
    private int report;

    @Builder
    public Challenge(int no, User user, Date start, Date end, ChallengeAccess access, ChallengeType type, String title, String content, boolean official, int report) {
        this.no = no;
        this.user = user;
        this.start = start;
        this.end = end;
        this.access = access;
        this.type = type;
        this.title = title;
        this.content = content;
        this.official = official;
        this.report = report;
    }
}
