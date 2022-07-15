package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.api.Dto.Interest.InterestListResponse;
import com.ssafy.challympic.domain.Interest;
import com.ssafy.challympic.domain.Subscription;
import com.ssafy.challympic.domain.Title;
import com.ssafy.challympic.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class UserResponse {
    private int user_no;
    private String user_email;
    private String user_nickname;
    private String user_title;
    private int file_no;
    private String file_path;
    private String file_savedname;
    private List<TitleListResponse> titles;
    private List<InterestListResponse> interests;
    private String auth;
    private List<SubscriptionListResponse> subscriptions;

    public UserResponse(User user) {
        this.user_no = user.getNo();
        this.user_email = user.getEmail();
        this.user_nickname = user.getNickname();
        this.user_title = user.getTitle();
        if (user.getMedia() == null) {
            this.file_no = 0;
        } else {
            this.file_no = user.getMedia().getNo();
            this.file_path = user.getMedia().getFile_path();
            this.file_savedname = user.getMedia().getFile_savedname();
        }
    }

    public UserResponse(User user, List<Title> titles, List<Interest> interests, List<Subscription> subscriptions) {
        this.user_no = user.getNo();
        this.user_email = user.getEmail();
        this.user_nickname = user.getNickname();
        this.user_title = user.getTitle();
        this.titles = titles.stream()
                .map(t -> new TitleListResponse(t))
                .collect(Collectors.toList());;
        this.interests = interests.stream()
                .map(i -> new InterestListResponse(i))
                .collect(Collectors.toList());;
        this.subscriptions = subscriptions.stream()
                .map(s -> new SubscriptionListResponse(s))
                .collect(Collectors.toList());;
        if(user.getMedia() == null){
            this.file_no = 0;
        }else{
            this.file_no = user.getMedia().getNo();
            this.file_path = user.getMedia().getFile_path();
            this.file_savedname = user.getMedia().getFile_savedname();
        }
        this.auth = user.getAuth().toString();
    }

}