package com.ssafy.challympic.api.Dto.Post;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostSaveRequest {

    private Integer user_no;
    private String post_content;
    private MultipartFile file;

    @Builder
    public PostSaveRequest(Integer user_no, String post_content, MultipartFile file) {
        this.user_no = user_no;
        this.post_content = post_content;
        this.file = file;
    }
}
