package com.ssafy.challympic.api.Dto.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostListRequest {
    private Integer user_no;
    private int challenge_no;

    @Builder
    public PostListRequest(int user_no, int challenge_no) {
        this.user_no = user_no;
        this.challenge_no = challenge_no;
    }

}
