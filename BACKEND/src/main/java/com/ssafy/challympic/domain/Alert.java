package com.ssafy.challympic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Alert {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_no")
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    private String content;

    private boolean confirm;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @Builder
    public Alert(User user, String content, boolean confirm) {
        this.user = user;
        this.content = content;
        this.confirm = confirm;
    }

    public Alert update(User user, String content) {
        this.user = user;
        this.content = content;
        return this;
    }
}
