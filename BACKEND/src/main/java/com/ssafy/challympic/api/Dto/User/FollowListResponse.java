package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;

@Getter
@NoArgsConstructor
public class FollowListResponse{
    private int user_no;
    private String user_nickname;
    private String user_title;
    private boolean isFollow;
    private String user_profile;

    @Builder
    public FollowListResponse(User user, boolean isFollow) {
        this.user_no = user.getNo();
        this.user_nickname = user.getNickname();
        this.user_title = user.getTitle();
        this.isFollow = isFollow;
        if(user.getMedia() != null){
            this.user_profile = user.getMedia().getFile_path()+ File.separator+user.getMedia().getFile_savedname();
        }
    }
}