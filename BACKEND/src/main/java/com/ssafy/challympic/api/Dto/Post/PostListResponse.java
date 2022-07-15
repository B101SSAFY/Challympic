package com.ssafy.challympic.api.Dto.Post;

import com.ssafy.challympic.api.Dto.Comment.CommentListResponse;
import com.ssafy.challympic.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.io.File;
import java.util.Date;
import java.util.List;

@Getter
public class PostListResponse {
    // 포스트 정보
    private int post_no;
    private String post_content;
    private int post_report;
    private Date post_regdate;
    private Date post_update;

    // 유저 타입
    private int user_no;
    private String user_nickname;
    private String user_title;
    private String user_profile;

    // 챌린지 타입
    private String challenge_type;
    private String challenge_name;
    private int challenge_no;

    // 미디어 정보
    private int file_no;
    private String file_path;
    private String file_savedname;

    // 좋아요 수
    private Integer LikeCnt;

    // 이 유저가 좋아요를 눌렀는지
    private boolean IsLike = false;

    // 댓글 리스트
    private List<CommentListResponse> commentList;

    @Builder
    public PostListResponse(Post post, Integer likeCnt, boolean isLike, List<CommentListResponse> commentList) {
        this.post_no = post.getNo();
        this.post_content = post.getPost_content();
        this.post_report = post.getPost_report();
        this.post_regdate = java.sql.Timestamp.valueOf(post.getCreatedDate());
        this.post_update = java.sql.Timestamp.valueOf(post.getModifiedDate());
        this.user_no = post.getUser().getNo();
        this.user_nickname = post.getUser().getNickname();
        this.user_title = post.getUser().getTitle();
        this.user_profile = post.getUser().getMedia()==null?null:post.getUser().getMedia().getFile_path()+ File.separator+post.getUser().getMedia().getFile_savedname();
        this.challenge_type = post.getChallenge().getType().name().toLowerCase();
        this.challenge_name = post.getChallenge().getTitle();
        this.challenge_no = post.getChallenge().getNo();
        this.file_no = post.getMedia().getNo();
        this.file_path = post.getMedia().getFile_path();
        this.file_savedname = post.getMedia().getFile_savedname();
        LikeCnt = likeCnt;
        IsLike = isLike;
        this.commentList = commentList;
    }

    @Builder
    public PostListResponse(Post post) {
        this.post_no = post.getNo();
        this.post_content = post.getPost_content();
        this.post_report = post.getPost_report();
        this.post_regdate = java.sql.Timestamp.valueOf(post.getCreatedDate());
        this.post_update = java.sql.Timestamp.valueOf(post.getModifiedDate());
        this.user_no = post.getUser().getNo();
        this.user_nickname = post.getUser().getNickname();
        this.user_title = post.getUser().getTitle();
        this.user_profile = post.getUser().getMedia()==null?null:post.getUser().getMedia().getFile_path()+ File.separator+post.getUser().getMedia().getFile_savedname();
        this.challenge_type = post.getChallenge().getType().name().toLowerCase();
        this.challenge_name = post.getChallenge().getTitle();
        this.challenge_no = post.getChallenge().getNo();
        this.file_no = post.getMedia().getNo();
        this.file_path = post.getMedia().getFile_path();
        this.file_savedname = post.getMedia().getFile_savedname();
    }
}
