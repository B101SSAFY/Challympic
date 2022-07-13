package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Activity.ActivityRequest;
import com.ssafy.challympic.api.Dto.Activity.ActivityResponse;
import com.ssafy.challympic.api.Dto.Activity.TagDto;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.domain.Tag;
import com.ssafy.challympic.service.ActivityService;
import com.ssafy.challympic.service.SearchService;
import com.ssafy.challympic.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ActivityApiController {

    private final ActivityService activityService;
    private final TagService tagService;
    private final SearchService searchService;

    @PostMapping("/activity")
    public Result setActivity(@RequestBody ActivityRequest request) { // TODO : Raw Type 모두 수정할 수도 있음. (http://happinessoncode.com/2018/02/08/java-generic-raw-type/)
        activityService.saveActivity(request);
        return new Result(true, HttpStatus.OK.value());
    }

    // TODO : 현재 로직을 Service로 낮춰야 할까요?
    @GetMapping("/activity/{userNo}")
    public Result getActivity(@PathVariable int userNo) {
        // TODO : 여기 주석 지워도 되나요?
//        List<Activity> mainUserActivityList = activityService.findActivityListByUserNo(userNo);

//        Map<Integer, List<Activity>> activityMap = new HashMap<>();
//        List<User> allUserList = userService.findAllUser();
//        int[][] intersectionCount = new int[allUserList.size()][2];
//        for(User user : allUserList) {
//            List<Activity> activityList = activityService.findActivityListByUserNo(user.getUser_no());
//            activityMap.put(user.getUser_no(), activityList);
//        }

        // 최신 태그 5개 불러오기
        List<Tag> allTagList = tagService.findRecentAllTagList();
        if(!allTagList.isEmpty()){
            allTagList.removeIf(t -> t.getIsChallenge() != null);
        }else{
            return new Result(true, HttpStatus.OK.value(), null);
        }

        if(userNo == 0){
            List<TagDto> tagDtos = new ArrayList<>();
            if(allTagList.size() == 0){
                return new Result(true, HttpStatus.OK.value(), null);
            }

            for (Tag tag : allTagList) {
                tagDtos.add(TagDto.builder().tag_no(tag.getNo()).tag_content(tag.getContent()).build());
            }

            if(tagDtos.size() < 5){
                return new Result(true, HttpStatus.OK.value(), ActivityResponse.builder().tagList(tagDtos).build());
            }else{
                return new Result(true, HttpStatus.OK.value(), ActivityResponse.builder().tagList(tagDtos.subList(0,5)).build());
            }
        }else {
            List<Tag> tagResponse = tagService.getTagsVer01(userNo);
            tagResponse.removeIf(t -> t.getIsChallenge() != null);
            List<TagDto> tagDtoResponse = new ArrayList<>();

            if (!tagResponse.isEmpty()) {
                tagDtoResponse = tagResponse.stream()
                        .map(t -> TagDto.builder()
                                .tag_no(t.getNo())
                                .tag_content(t.getContent())
                                .build())
                        .collect(Collectors.toList());
                return new Result(true, HttpStatus.OK.value(), new ActivityResponse(tagDtoResponse));
            } else {
                return new Result(true, HttpStatus.OK.value(), null);
            }
        }

    }
}
