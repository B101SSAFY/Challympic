package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_no")
    private Media media;

    private String post_content;

    private int post_report;

    @Builder
    public Post(User user, Challenge challenge, Media media, String post_content) {
        this.user = user;
        this.challenge = challenge;
        this.media = media;
        this.post_content = post_content;
        this.post_report = 0;
    }

    public Post update(Media media, String post_content){
        this.media = media;
        this.post_content = post_content;
        return this;
    }

    public Post updateReport(){
        this.post_report++;
        return this;
    }
}

