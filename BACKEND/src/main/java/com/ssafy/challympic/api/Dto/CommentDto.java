package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class CommentDto {
    private int comment_no;
    private int user_no;
    private String user_nickname;
    private String user_profile;
    private int post_no;
    private String comment_content;
    private LocalDateTime comment_regdate;
    private LocalDateTime comment_update;
    private int like_cnt;
    private int comment_report;
    private boolean IsLiked;
    private int file_no;

    public CommentDto(Comment comment, boolean IsLiked) {
        this.comment_no = comment.getNo();
        this.user_no = comment.getUser().getNo();
        this.user_nickname = comment.getUser().getNickname();
        if(comment.getUser().getMedia() != null)
            this.user_profile = comment.getUser().getMedia().getFile_path() +             File.separator
                    + comment.getUser().getMedia().getFile_savedname();
        this.post_no = comment.getPost().getNo();
        this.comment_content = comment.getContent();
        this.comment_regdate = comment.getCreatedDate();
        this.comment_update = comment.getModifiedDate();
        this.like_cnt = comment.getCommentLike().size();
        this.comment_report = comment.getReport();
        this.IsLiked = IsLiked;

    }
}
