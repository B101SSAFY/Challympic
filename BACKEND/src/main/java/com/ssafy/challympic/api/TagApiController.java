package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.Tag;
import com.ssafy.challympic.service.TagService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TagApiController {

    private final TagService tagService;

    /**
     * 개인 정보 태그 추가
     */
//    @PostMapping("/user/{userNo}/tag")
//    public void userTag(@PathVariable("userNo") int user_no, @RequestBody List<Integer> request){
//        request.forEach(tag_no -> {
//            Tag findTag = tagService.findOne(tag_no);
////            interestService
//        });
//    }

    @Data // TODO: DTO로 빼기
    @AllArgsConstructor
    static class Result<T>{
        private boolean isSuccess;
        private int code;
        private T data;

        public Result(boolean isSuccess, int code) {
            this.isSuccess = isSuccess;
            this.code = code;
        }
    }

    static class tagRequest { // TODO: dto로
        private List<Integer> tag_noList;
    }

    @Data // TODO: dto로
    static class TagDto{
        private int tag_no;
        private String tag_content;

        public TagDto(Tag tag) {
            this.tag_no = tag.getNo();
            this.tag_content = tag.getContent();
        }
    }

    @GetMapping("/allTags")
    public Result getAllTags(){
        List<Tag> allTagList = tagService.findRecentAllTagList();
        List<Tag> allTags = new ArrayList<>();
        for (Tag tag : allTagList) {
            if(tag.getIsChallenge() == null){
                allTags.add(tag);
            }
        }
        List<TagDto> TagResponse = allTags.stream() // TODO: service로
                .map(t -> new TagDto(t))
                .collect(Collectors.toList());

        return new Result(true, HttpStatus.OK.value(), TagResponse);
    }
}
