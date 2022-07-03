package com.ssafy.challympic.api.Dto.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequest {

    private int comment_no;

    private int user_no;

    private int post_no;

    private String comment_content;

    @Builder
    public CommentUpdateRequest(int comment_no, int user_no, int post_no, String comment_content) {
        this.comment_no = comment_no;
        this.user_no = user_no;
        this.post_no = post_no;
        this.comment_content = comment_content;
    }
}
