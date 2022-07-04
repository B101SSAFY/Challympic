package com.ssafy.challympic.api.Dto.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentLikeRequest {

    private int comment_no;

    private int user_no;

    @Builder
    public CommentLikeRequest(int comment_no, int user_no) {
        this.comment_no = comment_no;
        this.user_no = user_no;
    }
}
