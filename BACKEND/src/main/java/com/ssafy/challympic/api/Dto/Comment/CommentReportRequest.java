package com.ssafy.challympic.api.Dto.Comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentReportRequest {

    private int comment_no;

    @Builder
    public CommentReportRequest(int comment_no) {
        this.comment_no = comment_no;
    }
}
