package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Search extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "search_no")
    private int search_no;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_no")
    private User user;

    private int tag_no;

    private String tag_content;

    private String search_content;

    @Builder
    public Search(int search_no, User user, int tag_no, String tag_content, String search_content) {
        this.search_no = search_no;
        this.user = user;
        this.tag_no = tag_no;
        this.tag_content = tag_content;
        this.search_content = search_content;
    }

    public Search update(int tag_no, String tag_content) {
        this.tag_no = tag_no;
        this.tag_content = tag_content;

        return this;
    }
}
