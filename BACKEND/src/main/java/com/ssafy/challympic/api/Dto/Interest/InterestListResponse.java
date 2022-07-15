package com.ssafy.challympic.api.Dto.Interest;

import com.ssafy.challympic.domain.Interest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class InterestListResponse {
    private int interest_no;
    private int tag_no;
    private String tag_content;

    @Builder
    public InterestListResponse(Interest interest) {
        this.interest_no = interest.getNo();
        this.tag_no = interest.getTag().getNo();
        this.tag_content = interest.getTag().getContent();
    }
}
