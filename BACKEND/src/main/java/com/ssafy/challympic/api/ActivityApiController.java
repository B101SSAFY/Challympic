package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.ActivityService;
import com.ssafy.challympic.service.TagService;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class ActivityApiController {

    private final ActivityService activityService;
    private final TagService tagService;
    private final UserService userService;

    @PostMapping("/activity")
    public Result setActivity(@RequestBody ActivityRequest request) {
        Activity activity = new Activity();
        activity.setPost_no(request.getPostNo());
        activity.setUser_no(request.getUserNo());

        activityService.saveActivity(activity);
        return new Result(true, HttpStatus.OK.value());
    }

    private final int maxTag = 50;

    @GetMapping("/activity/{userNo}")
    public Result getActivity(@PathVariable int userNo) {
        List<Activity> activityList = activityService.findActivityListByUserNo(userNo);
        List<Tag> tagAll = new ArrayList<>();
        for(Activity activity : activityList) {
            List<PostTag> postTagList = tagService.findPostTagList(activity.getPost_no());
            for(PostTag postTag : postTagList) {
                tagAll.add(postTag.getTag());
            }
        }
//        int maxTag = tagService.getMaxTag();
        int[][] tagCount = new int[maxTag][2];

        for(int i = 0; i < maxTag; i++) tagCount[i][0] = i + 1;
        for(Tag tag : tagAll) {
            tagCount[tag.getTag_no()][1]++;
        }

        Arrays.sort(tagCount, (int[] o1, int[] o2) -> {
            return o1[1] - o2[1];
        });

        List<Tag> tagResponse = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            tagResponse.add(tagService.findOne(tagCount[i][0]));
        }

        return new Result(true, HttpStatus.OK.value(), new ActivityResponse(tagResponse));
    }

    @Data
    @AllArgsConstructor
    static class ActivityResponse {
        List<Tag> tagList;
    }

    @Data
    static class ActivityRequest {
        private int postNo;
        private int userNo;
    }
}