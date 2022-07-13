package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Activity.ActivityRequest;
import com.ssafy.challympic.api.Dto.Challenge.SearchTagChallengeResponse;
import com.ssafy.challympic.api.Dto.Challenge.SearchTrendResponse;
import com.ssafy.challympic.api.Dto.Post.SearchTagPostResponse;
import com.ssafy.challympic.api.Dto.Search.SearchRecentResponse;
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

        Map<String, List> data = new HashMap<>(); // TODO: 제네릭

        data.put("tagList", tagList);
        data.put("userList", userList);

        return new Result(true, HttpStatus.OK.value(), data);
    }



    /**
     * 태그 검색
     */
    @PostMapping("/search/tag")
    public Result searchTagList(@RequestBody TagSearchRequest request) {
        List<SearchTagChallengeResponse> challengeList = searchService.findChallengeListByTagContent(request);
        List<SearchTagPostResponse> postList = searchService.findPostListByTagContent(request);

        Map<String, List> data = new HashMap<>(); // TODO : 제네릭
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
            activityService.saveActivity(new ActivityRequest(request.getUser_no(), p.getNo()));
        }

        return new Result(true, HttpStatus.OK.value(), data);
    }

    @GetMapping("/search/recent/user/{userNo}")
    public Result searchRecentListByUser(@PathVariable int userNo){
        List<SearchRecentResponse> searchList = searchService.findTagListByUserNo(userNo);
        return new Result(true, HttpStatus.OK.value(), searchList);
    }

    @GetMapping("/search/trending")
    public Result searchTrending() {
        List<SearchTrendResponse> trendChallengeList = searchService.findTrendChallenge();
        return new Result(true, HttpStatus.OK.value(), trendChallengeList);
    }

    @GetMapping("/search/rank")
    public Result getRank() {
        List<UserNicknameResponse> userList = searchService.findRank();
        return new Result(true, HttpStatus.OK.value(), userList);
    }

}
