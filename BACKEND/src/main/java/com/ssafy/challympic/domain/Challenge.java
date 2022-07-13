package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.BaseTimeEntity;
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
public class Challenge extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    // TODO: 여기는 BaseTimeEntity처럼 LocalDateTime 안써도 되나요?
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
    public Challenge(User user, Date end, ChallengeAccess access, ChallengeType type, String title, String content, boolean official, int report) {
        this.user = user;
        this.end = end;
        this.access = access;
        this.type = type;
        this.title = title;
        this.content = content;
        this.official = official;
        this.report = report;
    }
}
