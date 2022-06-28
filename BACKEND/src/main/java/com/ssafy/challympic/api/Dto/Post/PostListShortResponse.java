package com.ssafy.challympic.api.Dto.Post;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Post;
import lombok.Builder;

public class PostListShortResponse {
    private int challenge_no;
    private int post_no;
    private int file_no;
    private String file_path;
    private String file_savedname;
    private String challenge_title;
    private int like_cnt;
    private int comment_cnt;

    @Builder
    public PostListShortResponse(Post post, Challenge challenge, int like_cnt, int comment_cnt) {
        this.challenge_no = post.getChallenge().getNo();
        this.post_no = post.getNo();
        this.file_no = post.getMedia().getFile_no();
        this.file_path = post.getMedia().getFile_path();
        this.file_savedname = post.getMedia().getFile_savedname();
        this.challenge_title = challenge.getTitle();
        this.like_cnt = like_cnt;
        this.comment_cnt = comment_cnt;
    }

}
