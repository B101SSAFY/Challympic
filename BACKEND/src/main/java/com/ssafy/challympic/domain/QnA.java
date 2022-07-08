package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class QnA extends BaseTimeEntity {

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

    @Builder
    public QnA(User user, String title, String question, String answer) {
        this.user = user;
        this.title = title;
        this.question = question;
        this.answer = answer;
    }

    public QnA update(String answer) {
        this.answer = answer;
        return this;
    }
}
