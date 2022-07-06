package com.ssafy.challympic.api.Dto.Comment;

import com.ssafy.challympic.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.text.SimpleDateFormat;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private int comment_no;
    private String comment_content;
    private int like_cnt;
    private String comment_regdate;
    private int comment_report;
    private int user_no;
    private String user_nickname;
    private String user_profile;

    public CommentResponse(Comment comment) {
        this.comment_no = comment.getNo();
        this.comment_content = comment.getContent();
        this.comment_report = comment.getReport();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.comment_regdate = formatter.format(comment.getCreatedDate());
        this.user_no = comment.getUser().getNo();
        this.user_nickname = comment.getUser().getNickname();
        if(comment.getUser().getMedia() != null){
            this.user_profile = comment.getUser().getMedia().getFile_path()+ File.separator+comment.getUser().getMedia().getFile_savedname();
        }
    }
}
