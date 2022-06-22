package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.Title;

public class TitleListResponse {
    private String title_name;

    public TitleListResponse(Title title) {
        this.title_name = title.getTitle_name();
    }
}
