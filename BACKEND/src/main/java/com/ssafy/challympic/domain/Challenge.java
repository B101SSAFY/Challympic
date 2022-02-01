package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Challenge {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_no")
    private int challenge_no;

//    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private int user_no;

    /**
     * temporal 오류
     */
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

//    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "title_no")
    private int title_no;

    // 생성 메소드
    public static Challenge createChallenge(int user_no, Date challenge_end, ChallengeAccess challenge_access, ChallengeType challenge_type, String challenge_title, String challenge_content, int title_no) {
        Challenge challenge = new Challenge();
        challenge.setUser_no(user_no);
        challenge.setChallenge_start(new Date());
        challenge.setChallenge_end(challenge_end);
        challenge.setChallenge_access(challenge_access);
        challenge.setChallenge_type(challenge_type);
        challenge.setChallenge_title(challenge_title);
        challenge.setChallenge_content(challenge_content);
        challenge.setTitle_no(title_no);
        return challenge;
    }
}
