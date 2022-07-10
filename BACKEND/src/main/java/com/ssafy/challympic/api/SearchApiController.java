package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.PostDto;
import com.ssafy.challympic.api.Dto.SearchDto;
import com.ssafy.challympic.api.Dto.Tag.TagSearchRequest;
import com.ssafy.challympic.api.Dto.User.UserNicknameResponse;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;
    private final UserService userService;
    private final ChallengeService challengeService;
    private final PostService postService;
    private final ActivityService activityService;

    @GetMapping("/search")
    public Result getSearchList() {
        List<Tag> tagList = searchService.findTagList();
        List<UserNicknameResponse> userList = searchService.findUserList();

        Map<String, List> data = new HashMap<>();

        data.put("tagList", tagList);
        data.put("userList", userList);

        return new Result(true, HttpStatus.OK.value(), data);
    }



    /**
     * 태그 검색
     */
    @PostMapping("/search/tag")
    public Result searchTagList(@RequestBody TagSearchRequest request) {
        List<ChallengeDto> challengeList = searchService.findChallengeListByTagContent(request);
        List<PostDto> postList = searchService.findPostListByTagContent(request);

        Map<String, List> data = new HashMap<>();
        data.put("challengeList", challengeList);
        data.put("postList", postList);

        if(request.getUser_no() > 0){
            // 검색 기록 저장
            User user = userService.findByNo(request.getUser_no());
            if(user != null) {
                searchService.saveSearchRecord(request);
            }

            List<Challenge> tagContainChallenges = challengeService.findChallengesByTag("#" + request.getTag_content());
            for(Challenge c : tagContainChallenges) {
                searchService.saveSearchChallenge(c, user);
            }
        }

        List<Post> tagContainPost = postService.getPostByTag(request.getTag_content());
        for(Post p : tagContainPost) {
            Activity activity = Activity.builder()
                    .post_no(p.getNo())
                    .user_no(request.getUser_no())
                    .build();
            activityService.saveActivity(activity);
        }

        return new Result(true, HttpStatus.OK.value(), data);
    }

    @GetMapping("/search/recent/user/{userNo}")
    public Result searchRecentListByUser(@PathVariable int userNo){
        List<SearchDto> searchList = searchService.findTagListByUserNo(userNo);
        return new Result(true, HttpStatus.OK.value(), searchList);
    }

    @GetMapping("/search/trending")
    public Result searchTrending() {
        List<ChallengeDto> trendChallengeList = searchService.findTrendChallenge();
        return new Result(true, HttpStatus.OK.value(), trendChallengeList);
    }

    @GetMapping("/search/rank")
    public Result getRank() {
        List<UserNicknameResponse> userList = searchService.findRank();
        return new Result(true, HttpStatus.OK.value(), userList);
    }

}
