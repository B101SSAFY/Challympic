package com.ssafy.challympic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class QnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String question;

    private String answer;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date question_regdate;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date answer_regdate;

    @Builder
    public QnA(int no, User user, String title, String question, String answer, Date question_regdate, Date answer_regdate) {
        this.no = no;
        this.user = user;
        this.title = title;
        this.question = question;
        this.answer = answer;
        this.question_regdate = question_regdate;
        this.answer_regdate = answer_regdate;
    }

    public QnA update(String answer, Date answer_regdate) {
        this.answer = answer;
        this.answer_regdate = answer_regdate;
        return this;
    }
}
