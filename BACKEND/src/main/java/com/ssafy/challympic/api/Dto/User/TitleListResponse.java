package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.Title;

// TODO: Getter, NoArgsConst
public class TitleListResponse {
    private String title_name;

    // TODO: builder
    public TitleListResponse(Title title) {
        this.title_name = title.getName();
    }
}
