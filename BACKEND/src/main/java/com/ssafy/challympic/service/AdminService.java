package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.QnA.QnADto;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.repository.*;
import com.ssafy.challympic.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
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
    private final SubscriptionService subscriptionService;


    private final S3Uploader s3Uploader;

    public List<User> userList(){
        return adminRepository.findAllUser();
    }

    @Transactional
    public void updateUserActive(int no){
        User findUser = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        //findUser.setUser_active(UserActive.INACTIVE);
    }

    @Transactional
    public void deleteUser(int no){
        User findUser = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        //userRepository.delete(findUser);
    }

    public List<ChallengeDto> challengeList(){
        List<Challenge> challengeList = adminRepository.findAllChallenge();
        return challengeList.stream()
                .map(c -> {
                    ChallengeDto challengeDto = ChallengeDto.builder()
                            .challenge_no(c.getNo())
                            .challenge_title(c.getTitle())
                            .user_email(c.getUser().getEmail())
                            .challenge_start(c.getStart())
                            .challenge_end(c.getEnd())
                            .challenge_official(c.isOfficial())
                            .challenge_report(c.getReport())
                            .build();
                    int post_cnt = postService.postCntByChallenge(c.getNo());
                    int subscription_cnt = subscriptionService.findSubscriptionByChallenge(c.getNo());
                    challengeDto = challengeDto.update(post_cnt, subscription_cnt);
                    return challengeDto;
                }).collect(Collectors.toList());
    }

    private final TagRepository tagRepository;

    @Transactional
    public void deleteChallenge(int challenge_no){
        Title findTitle = titleRepository.findByChallenge(challenge_no);
        titleRepository.delete(findTitle);

        List<ChallengeTag> challengeTags = challengeTagRepository.findByChallengeNo(challenge_no);
        if(!challengeTags.isEmpty()){
            challengeTagRepository.deleteAll(challengeTags);
        }

        List<Challenger> challengerList = challengerRepository.findByChallengeNo(challenge_no);
        if(!challengerList.isEmpty()){
            challengerRepository.deleteAll(challengerList);
        }
        Challenge findChallenge = challengeRepository.findById(challenge_no).get();
        adminRepository.deleteChallenge(findChallenge);
    }

    public List<Post> postList(){
        return adminRepository.findAllPost();
    }

    @Transactional
    public void deletePost(Post post){
        postRepository.delete(post);
    }

    public List<Comment> commentList(){
        return adminRepository.findAllComment();
    }

    @Transactional
    public void deleteComment(int comment_no){
        Comment findComment = commentRepository.findById(comment_no)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        commentRepository.delete(findComment);
    }

    @Transactional
    public void deletePostByChallenge(int challenge_no) {
        List<Post> postList = postRepository.findByChallengeNo(challenge_no);
        if(!postList.isEmpty()){
            for (Post post : postList) {
                Media media = post.getMedia();
                s3Uploader.deleteS3(media.getFile_path());
                mediaRepository.delete(media);
                postRepository.delete(post);
            }
        }
    }

    public List<QnADto> qnaList() {
        List<QnA> qnaList = adminRepository.findAllQnA();
        return qnaList.stream()
                .map(q -> {
                    QnADto qnaDto = QnADto.builder()
                            .qna_no(q.getNo())
                            .user_email(q.getUser().getEmail())
                            .qna_title(q.getTitle())
                            .qna_question(q.getQuestion())
                            .qna_answer(q.getAnswer())
                            .qna_answer_regdate(q.getAnswer_regdate())
                            .qna_question_regdate(q.getQuestion_regdate()).build();
                    if(q.getAnswer() == null) qnaDto = qnaDto.update(false);
                    else qnaDto = qnaDto.update(false);
                    return qnaDto;
                }).collect(Collectors.toList());
    }

    @Transactional
    public void updateQnA(int qna_no, String qna_answer){
        QnA findQnA = qnaRepository.findById(qna_no).orElseThrow(() -> new NoSuchElementException("존재하지 않는 QnA입니다."));
        findQnA.update(qna_answer, new Date());
    }
}
