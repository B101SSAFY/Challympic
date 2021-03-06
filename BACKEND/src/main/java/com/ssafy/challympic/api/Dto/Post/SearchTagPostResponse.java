package com.ssafy.challympic.api.Dto.Post;

import com.ssafy.challympic.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SearchTagPostResponse {

    private int post_no;
    private int user_no;
    private String user_nickname;
    private String user_title;
    private int challenge_no;
    private String chalenge_title;
    private int file_no;
    private String file_path;
    private String file_savedname;
    private String post_content;
    private int post_report;
    private LocalDateTime post_regdate;
    private LocalDateTime post_update;
    private int post_like_count;
    private int comment_count;
    private boolean IsLike = false;

    public SearchTagPostResponse(Post post, String challengeTitle, int postLikeCount, int postCommentCount, boolean isLike) {
        this.post_no = post.getNo();
        this.user_no = post.getUser().getNo();
        this.user_nickname = post.getUser().getNickname();
        this.user_title = post.getUser().getTitle();
        this.challenge_no = post.getChallenge().getNo();
        this.chalenge_title = challengeTitle;
        this.file_no = post.getMedia().getNo();
        this.file_path = post.getMedia().getFile_path();
        this.file_savedname = post.getMedia().getFile_savedname();
        this.post_content = post.getPost_content();
        this.post_report = post.getPost_report();
        this.post_regdate = post.getCreatedDate();
        this.post_update = post.getModifiedDate();
        this.post_like_count = postLikeCount;
        this.comment_count = postCommentCount;
        this.IsLike = isLike;
    }
}
