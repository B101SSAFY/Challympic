package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.domain.defaults.UserActive;
import com.ssafy.challympic.repository.*;
import com.ssafy.challympic.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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

    public List<Challenge> challengeList(){
        return adminRepository.findAllChallenge();
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
        Comment findComment = commentRepository.findOne(comment_no);
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

    public List<QnA> qnaList() {
        return adminRepository.findAllQnA();
    }

    @Transactional
    public void updateQnA(int qna_no, String qna_answer){
        QnA findQnA = qnaRepository.findOne(qna_no);
        findQnA.setQna_answer(qna_answer);
        findQnA.setQna_answer_regdate(new Date());
    }
}
