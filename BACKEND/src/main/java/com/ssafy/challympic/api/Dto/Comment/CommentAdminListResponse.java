package com.ssafy.challympic.api.Dto.Comment;

import com.ssafy.challympic.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentAdminListResponse {
    private int comment_no;

    private String comment_content;

    private int like_cnt;

    private LocalDateTime comment_regdate;

    private int comment_report;

    @Builder
    public CommentAdminListResponse(Comment comment, int like_cnt) {
        this.comment_no = comment.getNo();
        this.comment_content = comment.getContent();
        this.comment_regdate = comment.getCreatedDate();
        this.comment_report = comment.getReport();
        this.like_cnt = like_cnt;
    }
}
