package com.ssafy.challympic.api.Dto.Search;

import com.ssafy.challympic.domain.Search;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SearchRecentResponse {
    private int search_no;
    private int user_no;
    private int tag_no;
    private String tag_content;
    private String search_content;
    private LocalDateTime search_regdate;

    public SearchRecentResponse(Search s) {
        this.search_no = s.getNo();
        this.user_no = s.getUser().getNo();
        this.tag_no = s.getTag_no();
        this.tag_content = s.getTag_content();
        this.search_content = s.getContent();
        this.search_regdate = s.getCreatedDate();
    }
}
