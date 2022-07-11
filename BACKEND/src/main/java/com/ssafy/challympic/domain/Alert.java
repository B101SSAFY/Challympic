package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Alert extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_no") // TODO : 지워야하지 않을까요?
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    private String content;

    private boolean confirm;

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
