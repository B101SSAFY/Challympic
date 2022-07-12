package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Feed.FeedChallengeListResponse;
import com.ssafy.challympic.api.Dto.Feed.FeedPostListResponse;
import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.domain.PostLike;
import com.ssafy.challympic.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FeedService {

    private final ChallengeRepository challengeRepository;

    private final PostRepository postRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final PostLikeRepository postLikeRepository;

    private final CommentRepository commentRepository;

    public List<FeedChallengeListResponse> getChallengeByUserNo(int userNo){
        List<Challenge> challenges = challengeRepository.findByUser_No(userNo);
        return getFeedChallengeListResponses(challenges);
    }

    public List<FeedChallengeListResponse> getChallengeBySubscription(int userNo){
        List<Challenge> challenges = challengeRepository.findByUserNoFromSubscription(userNo);
        return getFeedChallengeListResponses(challenges);
    }

    private List<FeedChallengeListResponse> getFeedChallengeListResponses(List<Challenge> challenges) {
        List<FeedChallengeListResponse> collect = challenges.stream()
                .map(c -> {
                    List<Post> postList = postRepository.findByChallengeNo(c.getNo());
                    Post firstPost;
                    if (postList.isEmpty()) return new FeedChallengeListResponse();
                    else firstPost = postList.get(0);
                    int post_cnt = postRepository.findByChallengeNo(c.getNo()).size();
                    int subscription_cnt = subscriptionRepository.findAllByChallengeNo(c.getNo()).size();
                    return new FeedChallengeListResponse(firstPost, c, post_cnt, subscription_cnt);
                })
                .collect(Collectors.toList());
        return collect;
    }

    public List<FeedPostListResponse> getLikePostListByUserNo(int userNo){
        List<PostLike> userPostLike = postLikeRepository.findByUser_No(userNo);
        List<FeedPostListResponse> collect = userPostLike.stream()
                .map( pl -> {
                    Post post = postRepository.findById(pl.getNo())
                            .orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다."));
                    int likeCnt = postLikeRepository.countByPost_No(post.getNo());
                    int commentCnt = commentRepository.countByPost_No(post.getNo());
                    return new FeedPostListResponse(post, post.getChallenge(), likeCnt, commentCnt);
                }).collect(Collectors.toList());
        return collect;
    }

    public List<FeedPostListResponse> getPostListByUserNo(int userNo){
        List<Post> posts = postRepository.findByUserNo(userNo);
        List<FeedPostListResponse> collect = posts.stream()
                .map(p -> {
                    int likeCnt = postLikeRepository.countByPost_No(p.getNo());
                    int commentCnt = commentRepository.countByPost_No(p.getNo());
                    return new FeedPostListResponse(p, p.getChallenge(), likeCnt, commentCnt);
                }).collect(Collectors.toList());
        return collect;
    }

}
