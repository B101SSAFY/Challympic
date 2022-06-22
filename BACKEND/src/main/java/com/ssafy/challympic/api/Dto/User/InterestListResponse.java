package com.ssafy.challympic.api.Dto.User;

import com.ssafy.challympic.domain.Interest;

public class InterestListResponse {
    private int interest_no;
    private int tag_no;
    private String tag_content;

    public InterestListResponse(Interest interest) {
        this.interest_no = interest.getInterest_no();
        this.tag_no = interest.getTag().getTag_no();
        this.tag_content = interest.getTag().getTag_content();
    }
}
