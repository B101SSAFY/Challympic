package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.Challenge.ChallengeAdminListResponse;
import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.Comment.CommentAdminListResponse;
import com.ssafy.challympic.api.Dto.QnA.QnAAdminListResponse;
import com.ssafy.challympic.api.Dto.QnA.QnADto;
import com.ssafy.challympic.api.Dto.User.UserListResponse;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.repository.*;
import com.ssafy.challympic.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengerRepository challengerRepository;
    private final TitleRepository titleRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final QnARepository qnaRepository;
    private final MediaRepository mediaRepository;
    private final ChallengeTagRepository challengeTagRepository;

    private final PostService postService;

    private final ChallengeService challengeService;

    private final CommentService commentService;

    private final CommentLikeService commentLikeService;
    private final SubscriptionService subscriptionService;


    private final S3Uploader s3Uploader;

    public List<UserListResponse> userList(){
        return userRepository.findAll()
                .stream().map(
                        u -> {
                            int report = postService.postReportCntByUser(u.getNo())
                                    + challengeService.challengeReportCntByUser(u.getNo())
                                    + commentService.commentReportCntByUser(u.getNo());
                            return new UserListResponse(u, report);
                        }
                ).collect(Collectors.toList());
    }

    @Transactional
    public void updateUserActive(int userNo){
        User findUser = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        findUser.updateActive();
    }

    @Transactional
    public void deleteUser(int no){
        User findUser = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        userRepository.delete(findUser);
    }

    public List<ChallengeAdminListResponse> challengeList(){
        List<Challenge> challengeList = challengeService.findAllChallenge();
        return challengeList.stream()
                .map(c -> {
                    int postCnt = postService.postCntByChallenge(c.getNo());
                    int subscriptionCnt = subscriptionService.findSubscriptionByChallenge(c.getNo());
                    return new ChallengeAdminListResponse(c, postCnt, subscriptionCnt);
                }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteChallenge(int challengeNo){
        Title findTitle = titleRepository.findByChallengeNo(challengeNo);
        titleRepository.delete(findTitle);

        List<ChallengeTag> challengeTags = challengeTagRepository.findByChallengeNo(challengeNo);
        if(!challengeTags.isEmpty()){
            challengeTagRepository.deleteAll(challengeTags);
        }

        List<Challenger> challengerList = challengerRepository.findByChallengeNo(challengeNo);
        if(!challengerList.isEmpty()){
            challengerRepository.deleteAll(challengerList);
        }
        Challenge findChallenge = challengeRepository.findById(challengeNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 챌린지가 없습니다."));
        challengeRepository.delete(findChallenge);
    }

    public List<Post> postList(){
        return postRepository.findAll();
    }

    @Transactional
    public void deletePost(Post post){
        postRepository.delete(post);
    }

    public List<CommentAdminListResponse> commentList(){
        return commentRepository.findAll()
                .stream().map(c -> new CommentAdminListResponse(c, commentLikeService.findLikeCntByComment(c.getNo())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(int commentNo){
        Comment findComment = commentRepository.findById(commentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        commentRepository.delete(findComment);
    }

    @Transactional
    public void deletePostByChallenge(int challengeNo) {
        List<Post> postList = postRepository.findByChallengeNo(challengeNo);
        if(!postList.isEmpty()){
            for (Post post : postList) {
                Media media = post.getMedia();
                s3Uploader.deleteS3(media.getFile_path());
                mediaRepository.delete(media);
                postRepository.delete(post);
            }
        }
    }

    public List<QnAAdminListResponse> qnaList() {
        List<QnA> qnaList = qnaRepository.findAll();
        return qnaList.stream()
                .map(q -> new QnAAdminListResponse(q))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateQnA(int qna_no, String qna_answer){
        QnA findQnA = qnaRepository.findById(qna_no).orElseThrow(() -> new NoSuchElementException("존재하지 않는 QnA입니다."));
        findQnA.update(qna_answer);
    }
}
