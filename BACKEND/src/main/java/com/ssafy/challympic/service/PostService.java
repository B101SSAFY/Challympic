package com.ssafy.challympic.service;


import com.ssafy.challympic.api.Dto.Post.PostSaveRequest;
import com.ssafy.challympic.api.Dto.Post.PostUpdateRequest;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.repository.PostLikeRepository;
import com.ssafy.challympic.repository.PostRepository;
import com.ssafy.challympic.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostLikeRepository postLikeRepository;

    private final ChallengeService challengeService;

    private final UserService userService;

    private final S3Uploader s3Uploader;

    private final MediaService mediaService;

    private final TagService tagService;

    private final AlertService alertService;

    public Post getPost(int postNo){
        return postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다."));
    }

    public List<Post> getPostList(int challengeNo){
        return postRepository.findByChallengeNo(challengeNo);
    }

    // TODO : 변수 필요 없음
    public List<Post> getRecentPostList(int limit){
        return postRepository.findTop50ByOrderByNoDesc();
    }

    @Transactional
    public int save(int challengeNo, PostSaveRequest request) {

        // 플로우 시작
        Media media = null;
        MultipartFile files = null;
        Challenge challenge = challengeService.findChallengeByChallengeNo(challengeNo);
        User user = userService.findByNo(request.getUser_no());
        int postId = 0;

        // 챌린저 목록 가져옴
        List<Challenger> challengerList = challengeService.getChallengerByChallengeNo(challengeNo);
        boolean isChallenger = false;

        // 챌린저 목록이 지정되어 있거나 포스트 작성자가 챌린지 작성자인 경우
        if(!challengerList.isEmpty()){
            for(Challenger challenger : challengerList){
                if(challenger.getUser().getNo() == user.getNo()) {
                    isChallenger = true;
                    break;
                }
            }

            if(request.getUser_no() == challenge.getUser().getNo())
                isChallenger = true;
        }

        if (!challengerList.isEmpty() && !isChallenger) throw new IllegalArgumentException("해당 유저는 챌린지에 참여할 수 없습니다.");


        try {

            files = request.getFile();

            // 확장자 체크
            String fileType = getFileType(files);

            if(fileType == null)
                // 지원하지 않는 확장자
                throw new IllegalArgumentException("해당 파일은 챌린지에 참여할 수 없습니다.");

            if(!fileType.equals(challenge.getType().name())){
                // 챌린지와 확장자 명이 다름
                throw new IllegalArgumentException("해당 파일은 챌린지에 참여할 수 없습니다.");
            }

            // png/jpg, mp4 <- 확장자
            media = s3Uploader.upload(files, fileType.toLowerCase(), "media");

            if(media == null) {
                // AWS S3 업로드 실패
                throw new IllegalArgumentException("해당 파일은 챌린지에 참여할 수 없습니다.");
            }

            int file_no = mediaService.saveMedia(media);

            // 본문 텍스트 파싱
            String content = request.getPost_content();
            String[] splitSharp = content.split(" ");

            for(String str : splitSharp){
                if(str.startsWith("#")){
                    // #을 분리하고 태그명만 추출
                    tagService.saveTag(str);
                }
            }

            Post post = Post.builder()
                    .challenge(challenge)
                    .post_content(request.getPost_content())
                    .media(media)
                    .user(user)
                    .build();

            postId = postRepository.save(post).getNo();

            for(String str : splitSharp) {
                if(str.startsWith("#")) {
                    Tag tag = tagService.findTagByTagContent(str);
                    PostTag postTag = PostTag.builder()
                            .post(post)
                            .tag(tag)
                            .build();
                    tagService.savePostTag(postTag);
                }
            }

            // 태그한 사람 알림
            for(String str : splitSharp) {
                if(str.startsWith("@")) {
                    // 태그 당한 닉네임
                    String user_nickname = str.substring(1);

                    Alert alert = new Alert();
                    User alertUser = userService.findByNickname(user_nickname);
                    // 태그 당한 유저
                    if(alertUser == null) {
                        continue;
                    }

                    User writer = userService.findByNo(request.getUser_no());
                    alert = Alert.builder()
                            .user(alertUser)
                            .content(writer.getNickname() + "님이 태그했습니다.")
                            .build();
                    alertService.saveAlert(alert);
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return postId;
    }

    /**
     *  프론트 단에서 파일을 받아 확장자에 따라 파일 타입을 결정
     *      - String으로 할지 Enum으로 할지 결정 필요
     * */
    private String getFileType(MultipartFile files){
        String fileName = files.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if(extension.equals("mp4") || extension.equals("MP4"))
            return "VIDEO";

        extension = extension.toLowerCase();
        if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))
            return "IMAGE";

        if(extension.equals("avi"))
            return "VIDEO";

        return null;
    }

    @Transactional
    public int update(Integer postNo, PostUpdateRequest request) throws Exception {
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트가 존재하지 않습니다."));

        s3Uploader.deleteS3(post.getMedia().getFile_path());
        mediaService.delete(post.getMedia().getNo());

        String type = getFileType(request.getFile());
        Media media = s3Uploader.upload(request.getFile(), type.toLowerCase(), "media");

        // 본문 텍스트 파싱
        String content = request.getPost_content();
        String[] splitSharp = content.split(" ");

        for(String str : splitSharp){
            if(str.startsWith("#")){
                // #을 분리하고 태그명만 추출
                tagService.saveTag(str);
            }
        }

        return post.update(media, content).getNo();
    }

    @Transactional
    public void updateReport(int postNo){
        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다."));

        post.updateReport();
    }

    @Transactional
    public void delete(int postNo){

        Post post = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다."));

        Media media = post.getMedia();

        s3Uploader.deleteS3(media.getFile_path());

        mediaService.delete(media.getNo());

        List<PostTag> ptl = tagService.findPostTagList(postNo);

        for(PostTag pt : ptl){
            tagService.deletePostTag(pt);
        }

        List<PostLike> byPost_no = postLikeRepository.findByPost_No(postNo);
        for (PostLike postLike : byPost_no) {
            postLikeRepository.delete(postLike); // TODO: 243 ~ 245 postLikeRepository.deleteAll(byPost_no);로 수정 가능
        }

        postRepository.delete(post);
    }

    public int postCntByChallenge(int challengeNo){
        return postRepository.findByChallengeNo(challengeNo).size();
    }

    public int postReportCntByUser(int userNo){
        List<Post> findPostList = postRepository.findByUserNo(userNo);
        int reportCnt = 0;
        for (Post post : findPostList) {
            reportCnt += post.getPost_report();
        }
        return reportCnt;
    }

    public List<Post> getPostListByUserNo(int userNo) {
        return postRepository.findByUserNo(userNo);
    }

    public List<Post> getLikePostListByUserNo(int userNo) {
        List<PostLike> userPostLike = postLikeRepository.findByUser_No(userNo);
        List<Post> posts = new ArrayList<>();
        if(!userPostLike.isEmpty()){
            posts = userPostLike.stream()
                    .map(pl -> {
                        Post post = postRepository.findById(pl.getPost().getNo()) // TODO: 선언 없이 바로 return할 수 있도록 수정
                                .orElseThrow(() -> new IllegalArgumentException("해당 포스트가 없습니다."));
                        return post;
                    }).collect(Collectors.toList());
        }
        return posts;
    }

    public boolean getPostLikeByPostNoAndUserNo(int post_no, int user_no) {
        // TODO : isPresent()로 바로 return
        if (postLikeRepository.findByPost_NoAndUser_No(post_no, user_no).isEmpty()) return false;
        return true;
    }

    // TODO: 주석부분 확인 후 삭제
//    public int getPostLikeCountByPostNo(int post_no) {
//        List<CommentLike> commentLikeList = postRepository.findPostLikeByPostNo(post_no);
//        return commentLikeList.size();
//    }

    public List<Post> getPostByTag(String tag_content) {
        return postRepository.findByTag(tag_content);
    }
}
