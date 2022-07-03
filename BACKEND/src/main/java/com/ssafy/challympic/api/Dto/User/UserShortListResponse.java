package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.Media;
import com.ssafy.challympic.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserShortListResponse {
    private int user_no;
    private String user_nickname;
    private String user_title;
    private int file_no;
    private String file_path;
    private String file_savedname;
    private Boolean isFollowing;

    @Builder
    public UserShortListResponse(User user, Media media, boolean isFollowing) {
        this.user_no = user.getNo();
        this.user_nickname = user.getNickname();
        this.user_title = user.getTitle();
        if(media != null){
            this.file_no = media.getNo();
            this.file_path = media.getFile_path();
            this.file_savedname = media.getFile_savedname();
        }
        this.isFollowing = isFollowing;
    }
}
