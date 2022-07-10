package com.ssafy.challympic.api.Dto.Feed;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedChallengeListResponse {

    private int challenge_no;
    private boolean isVideo;
    private int post_no;
    private int file_no;
    private String file_path;
    private String file_savedname;
    private String challenge_title;
    private int post_cnt;
    private int subscription_cnt;

    @Builder
    public FeedChallengeListResponse(Post post, Challenge challenge, int post_cnt, int subscription_cnt) {
        this.challenge_no = post.getChallenge().getNo();
        if(challenge.getType() == ChallengeType.VIDEO) this.isVideo = true;
        this.post_no = post.getNo();
        this.file_no = post.getMedia().getNo();
        this.file_path = post.getMedia().getFile_path();
        this.file_savedname = post.getMedia().getFile_savedname();
        this.challenge_title = challenge.getTitle();
        this.post_cnt = post_cnt;
        this.subscription_cnt = subscription_cnt;
    }
}
