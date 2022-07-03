package com.ssafy.challympic.api.Dto.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDeleteRequest {

    private int comment_no;

    private int user_no;

    private int post_no;

    @Builder
    public CommentDeleteRequest(int comment_no, int user_no, int post_no) {
        this.comment_no = comment_no;
        this.user_no = user_no;
        this.post_no = post_no;
    }
}
