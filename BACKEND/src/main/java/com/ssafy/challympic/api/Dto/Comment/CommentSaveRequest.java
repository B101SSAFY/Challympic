package com.ssafy.challympic.api.Dto.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequest {

    private int user_no;

    private int post_no;

    private String comment_content;

    @Builder
    public CommentSaveRequest(int user_no, int post_no, String comment_content) {
        this.user_no = user_no;
        this.post_no = post_no;
        this.comment_content = comment_content;
    }
}
