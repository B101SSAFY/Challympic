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
public class Search extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_no")
    private User user;

    // TODO: 지금 태그가 들어오지 않고 tag_no, tag_content로만 들어오는데 이대로 가도 괜찮겠죠..?
    private int tag_no;

    private String tag_content;

    private String content;

    @Builder
    public Search(int no, User user, int tag_no, String tag_content, String content) {
        this.no = no;
        this.user = user;
        this.tag_no = tag_no;
        this.tag_content = tag_content;
        this.content = content;
    }

    public Search update(int tag_no, String tag_content) {
        this.tag_no = tag_no;
        this.tag_content = tag_content;

        return this;
    }
}
