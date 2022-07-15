package com.ssafy.challympic.api.Dto.Tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagSearchRequest {
    private int user_no;
    private String tag_content;

    @Builder
    public TagSearchRequest(String tag_content) {
        this.tag_content = tag_content;
    }
}
