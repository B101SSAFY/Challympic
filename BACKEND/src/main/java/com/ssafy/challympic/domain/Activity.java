package com.ssafy.challympic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Activity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    private int user_no;

    private int post_no;

    @Builder
    public Activity(int user_no, int post_no) {
        this.user_no = user_no;
        this.post_no = post_no;
    }
}
