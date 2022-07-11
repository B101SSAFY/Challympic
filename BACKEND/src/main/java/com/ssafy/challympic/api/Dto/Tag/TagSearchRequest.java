package com.ssafy.challympic.api.Dto.Tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor // TODO: NoArgConst가 필요
public class TagSearchRequest {
    private int user_no;
    private String tag_content;

    @Builder // TODO : req에서는 user_no도 넣어야함. AllArgs어노테이션이 있어서 괜찮았던 것 같음.
    public TagSearchRequest(String tag_content) {
        this.tag_content = tag_content;
    }
}
