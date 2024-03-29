package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Challenge.SearchTagChallengeResponse;
import com.ssafy.challympic.api.Dto.Challenge.SearchTrendResponse;
import com.ssafy.challympic.api.Dto.Post.SearchTagPostResponse;
import com.ssafy.challympic.api.Dto.Search.SearchRecentResponse;
import com.ssafy.challympic.api.Dto.Tag.TagSearchRequest;
import com.ssafy.challympic.api.Dto.User.UserNicknameResponse;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final TagRepository tagRepository;
    private final ChallengeRepository challengeRepository;

    private final ChallengeTagRepository challengeTagRepository;

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SearchChallengeRepository searchChallengeRepository;
    private final PostService postService;
    private final ChallengeService challengeService;
    private final SubscriptionService subscriptionService;
    private final PostLikeService postLikeService;
    private final CommentService commentService;

    public List<Tag> findTagList() {
        List<Tag> tagList = tagRepository.findAll();
        tagList.removeIf(t -> t.getIsChallenge() != null);
        return tagList;
    }

    public List<UserNicknameResponse> findUserList(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(u -> new UserNicknameResponse(u.getNo(), u.getNickname()))
                .collect(Collectors.toList());
    }

    public List<SearchRecentResponse> findTagListByUserNo(int userNo) {
        List<Search> searches = searchRepository.findAllByUserNo(userNo);
        return searches.stream()
                .map(s -> new SearchRecentResponse(s))
                .collect(Collectors.toList());
    }

    public List<SearchTagChallengeResponse> findChallengeListByTagContent(TagSearchRequest request) {
        List<ChallengeTag> challenges = challengeTagRepository.findByTag_Content(request.getTag_content());
        return challenges.stream()
                .map(ct -> {
                    List<Post> postListByChallengeNo = postService.getPostList(ct.getNo());
                    List<SearchTagPostResponse> postList = postToDto(postListByChallengeNo, request.getUser_no());
                    boolean isSubscription = subscriptionService.findSubscriptionByChallengeAndUser(ct.getNo(), request.getUser_no()) != null;
                    return new SearchTagChallengeResponse(ct.getChallenge(), postList, isSubscription);
                })
                .collect(Collectors.toList());
    }

    private List<SearchTagPostResponse> postToDto(List<Post> posts, Integer userNo) {
        return posts.stream()
                .map(p -> {
                    String challengeTitle = challengeService.findChallengeByChallengeNo(p.getChallenge().getNo()).getTitle();
                    List<PostLike> postLikeList = postLikeService.getPostLikeListByPostNo(p.getNo());
                    int commentCount = commentService.postCommentCnt(p.getNo());
                    boolean isLike = postService.getPostLikeByPostNoAndUserNo(p.getNo(), userNo);
                    return new SearchTagPostResponse(p,challengeTitle, postLikeList.size(), commentCount, isLike);
                })
                .collect(Collectors.toList());
    }

    public List<SearchTagPostResponse> findPostListByTagContent(TagSearchRequest request) {
        List<Post> posts = postRepository.findFromPostTagByTagContent(request.getTag_content());
        return postToDto(posts, request.getUser_no());
    }

    public List<SearchTrendResponse> findTrendChallenge() {
        List<Challenge> searchedChallenges = challengeRepository.findFromSearchChallenge();
        List<Challenge> allChallenge = challengeRepository.findAll();
        int challengeSize = allChallenge.size();
        List<int[]> challengeCount = new ArrayList<>();

        for(Challenge c : searchedChallenges) {
            boolean isFind = false;
            for(int[] count : challengeCount) {
                if(count[0] == c.getNo()) {
                    count[1]++;
                    isFind = true;
                }
            }
            if(!isFind) {
                challengeCount.add(new int[]{c.getNo(), 0});
            }
        }

        challengeCount.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });

        List<Challenge> trendChallenge = new ArrayList<>();
        System.out.println(challengeSize);
        for(int i = 0; i <= (Math.min(challengeSize-1, 4)); i++) {
            int challengeNo = challengeCount.get(i)[0];
            trendChallenge.add(challengeRepository
                    .findById(challengeNo).get());
        }

        return trendChallenge.stream()
                .map(SearchTrendResponse::new)
                .collect(Collectors.toList());
    }

    public List<UserNicknameResponse> findRank() {
        List<User> users = userRepository.findRank();
        return users.stream()
                .map(u -> UserNicknameResponse.builder()
                        .user_no(u.getNo())
                        .user_nickname(u.getNickname())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveSearchRecord(TagSearchRequest request) {
        String search_content = request.getTag_content();
        User user = userRepository.findById(request.getUser_no()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));
        Tag tag = tagRepository.findByContent(search_content)
                .orElseThrow(() -> new IllegalArgumentException("해당 태그가 없습니다."));
        Search search = Search.builder()
                .content(search_content)
                .user(user)
                .build();
        if(tag != null) {
            search = search.update(tag.getNo(), tag.getContent());
        }
        searchRepository.save(search);
    }

    @Transactional
    public void saveSearchChallenge(Challenge challenge, User user){
        SearchChallenge searchChallenge = SearchChallenge.builder()
                .user(user)
                .challenge(challenge)
                .build();
        searchChallengeRepository.save(searchChallenge);
    }
}
