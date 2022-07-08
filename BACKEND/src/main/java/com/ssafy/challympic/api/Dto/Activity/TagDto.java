package com.ssafy.challympic.api.Dto.Activity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagDto {

    private int tag_no;
    private String tag_content;

    @Builder
    public TagDto(int tag_no, String tag_content) {
        this.tag_no = tag_no;
        this.tag_content = tag_content;
    }
}
