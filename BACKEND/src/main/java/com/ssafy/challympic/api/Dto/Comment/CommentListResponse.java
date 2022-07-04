package com.ssafy.challympic.api.Dto.Comment;

import com.ssafy.challympic.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor
public class CommentListResponse {
    private int comment_no;
    private int user_no;
    private String user_nickname;
    private String user_profile;
    private int post_no;
    private String comment_content;
    private Date comment_regdate;
    private Date comment_update;
    private int like_cnt;
    private int comment_report;
    private boolean IsLiked;
    private int file_no;

    @Builder
    public CommentListResponse(Comment comment, boolean IsLiked) {
        this.comment_no = comment.getNo();
        this.user_no = comment.getUser().getNo();
        this.user_nickname = comment.getUser().getNickname();
        if(comment.getUser().getMedia() != null)
            this.user_profile = comment.getUser().getMedia().getFile_path() + File.separator
                    + comment.getUser().getMedia().getFile_savedname();
        this.post_no = comment.getPost().getNo();
        this.comment_content = comment.getContent();
        this.comment_regdate = comment.getRegdate();
        this.comment_update = comment.getUpdate();
        this.like_cnt = comment.getCommentLike().size();
        this.comment_report = comment.getReport();
        this.IsLiked = IsLiked;
    }
}
