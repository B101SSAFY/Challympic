package com.ssafy.challympic.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    /**
     * 내가 follow한 사람
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "src_no")
    private User srcUser;

    /**
     * 나를 follow한 사람
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dest_no")
    private User destUser;

    @Builder
    public Follow(User src_no, User dest_no) {
        this.srcUser = src_no;
        this.destUser = dest_no;
    }
}
