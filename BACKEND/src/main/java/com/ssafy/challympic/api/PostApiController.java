package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.Comment.CommentListResponse;
import com.ssafy.challympic.api.Dto.CommentDto;
import com.ssafy.challympic.api.Dto.Post.*;
import com.ssafy.challympic.api.Dto.User.UserShortListResponse;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin("*")
@RestController
@Slf4j
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final ChallengeService challengeService;
    private final PostLikeService postLikeService;
    private final UserService userService;
    private final FollowService followService;
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    @GetMapping("/main/recent/post")
    public Result getRecentPosts(@RequestParam(required = false) Integer userNo){
        // 최대 50개 가져오기
        List<Post> postList = postService.getRecentPostList();
        List<PostListResponse> collect = new ArrayList<>();

        for(Post post : postList){
            List<PostLike> postLikeList = postLikeService.getPostLikeListByPostNo(post.getNo());
            int likeCnt = postLikeList.size();
            boolean isLike = postLikeService.getPostLikeByUserNoPostNo(post.getNo(), userNo);

            List<CommentListResponse> commentList = commentService.findByPost(post.getNo(), userNo);
            collect.add(PostListResponse.builder()
                    .post(post)
                    .isLike(isLike)
                    .likeCnt(likeCnt)
                    .commentList(commentList)
                    .build());
        }

        return new Result(true, HttpStatus.OK.value(), collect);
    }

    /**
     *  챌린지 번호로 포스트 가져오기(챌린지로 확인 예정)
     * */
    @PostMapping("/challenge/post")
    public Result list(@RequestBody PostListRequest request){

        try{
            // 챌린지 정보
            Challenge challenge = challengeService.findChallengeByChallengeNo(request.getChallenge_no());
            if(challenge == null) return new Result(false, HttpStatus.BAD_REQUEST.value());
            String type = challenge.getType().name().toLowerCase();
            // 포스트 리스트
            List<Post> postList = postService.getPostList(request.getChallenge_no());

            List<PostListResponse> collect = new ArrayList<>();

            for(Post post : postList){
                List<PostLike> postLikeList = postLikeService.getPostLikeListByPostNo(post.getNo());
                int likeCnt = 0;
                // 좋아요 수
                if(postLikeList.size() != 0){
                    likeCnt = postLikeList.size();
                }

                boolean isLike = false;
                if(request.getUser_no() != null) {
                    isLike = postLikeService.getPostLikeByUserNoPostNo(post.getNo(), request.getUser_no());
                }

                List<CommentListResponse> commentList = commentService.findByPost(post.getNo(), request.getUser_no());

                collect.add(PostListResponse.builder()
                        .post(post)
                        .isLike(isLike)
                        .likeCnt(likeCnt)
                        .commentList(commentList)
                        .build());
            }
            return new Result(true, HttpStatus.OK.value(), collect);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    /** 
     *  해당 게시글을 좋아요한 유저의 목록(Complete)
     *      - 유저의 정보를 모두 가져올 수도 있음
     *      - 유저 번호와 유저 닉네임만 전달받을 수도 있음
     *      - 현재 번호만 가져옴
     * */
    @GetMapping("/post/{postNo}/like/{userNo}")
    public Result likeList(@PathVariable("postNo") int postNo, @PathVariable("userNo") int userNo){

        try{
            // PostLike에서 게시글이 post인 것 추출
            // 받는 쪽에서 길이 구해서 좋아요 수 출력
            List<PostLike> postLikeList = postLikeService.getPostLikeListByPostNo(postNo);

            // 좋아요 누른 유저 정보만 가져오기
            List<UserShortListResponse> userList = new ArrayList<>();
            for(PostLike postLike : postLikeList){
                User user = userService.findByNo(postLike.getUser().getNo());
                boolean follow = followService.isFollow(userNo, user.getNo());
                userList.add(new UserShortListResponse(user, user.getMedia(), follow));
            }
            return new Result(true, HttpStatus.OK.value(), userList);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }
    
    /** 
     *  포스트 등록하기(Complete)
     *          alter table post
     *          convert to char set utf8;
     *      - Chaalenge : 챌린지 이름으로 검색
     *      - Challenge : 해당 챌린지 사진/영상 포맷 가져오기
     *      - File명 프론트 단에서 거르기
     *      - 확장자 프론트에서 거를지 백에서 거를지 결정
     *      - File 도메인 변경 및 테이블 명 변경 -> Media, File이라는 io의 객체와 이름이 겹침
     * */
    @PostMapping("/challenge/{challengeNo}/post")
    public Result create(@PathVariable("challengeNo") int challengeNo, PostSaveRequest request) throws IOException {

        try{
            int returnNo = postService.save(challengeNo, request);
            Post post = postService.getPost(returnNo);
            PostListResponse returnPost = PostListResponse.builder()
                    .post(post)
                    .build();
            return new Result(true, HttpStatus.OK.value(), returnPost);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }

    }
    
    /**
     *  기존 포스트를  수정하는 함수
     *      - Post를 postNo로 가져온다.
     *      - 파일 번호를 가져와서 파일을 업데이트
     *      - 포스트 업데이트
     * */
    @PutMapping("/challenge/{challengeNo}/post/{postNo}")
    public Result update(@PathVariable("challengeNo") int challengeNo, @PathVariable("postNo") int postNo, PostUpdateRequest request) throws Exception {

        try{
            int returnNo = postService.update(postNo, request);
            Post post = postService.getPost(returnNo);
            int likeCnt = postLikeService.getPostLikeListByPostNo(postNo).size();
            boolean isLike = postLikeService.getPostLikeByUserNoPostNo(post.getNo(), post.getUser().getNo());

            List<CommentListResponse> commentList = commentService.findByPost(post.getNo(), post.getUser().getNo());
            PostListResponse returnPost = PostListResponse.builder()
                    .post(post)
                    .isLike(isLike)
                    .likeCnt(likeCnt)
                    .commentList(commentList)
                    .build();
            return new Result(true, HttpStatus.OK.value(), returnPost);
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }

    }

    
    /** 
     *  저장된 포스트를 삭제하는 함수
     * */
    @DeleteMapping("/post/{postNo}")
    public Result delete(@PathVariable("postNo") int postNo){

        try{
            postService.delete(postNo);
            return new Result(true, HttpStatus.OK.value());
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     *  좋아요 클릭 처리(Complete)
     * */
    @PostMapping("/post/{postNo}/like/{userNo}")
    public Result like(@PathVariable("postNo") int postNo, @PathVariable("userNo") int userNo){

        try{
            if (postLikeService.getPostLikeByUserNoPostNo(postNo, userNo)){
                postLikeService.delete(postNo, userNo);
            }else{
                // insert
                postLikeService.save(PostLike.builder()
                        .post(postService.getPost(postNo))
                        .user(userService.findByNo(userNo))
                        .build());
            }
            return new Result(true, HttpStatus.OK.value());
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @GetMapping("/post/{userNo}")
    public Result postByUser(@PathVariable("userNo") int user_no){
        List<PostListShortResponse> responses = postService.getPostListByUserNo(user_no);
        return new Result(true, HttpStatus.OK.value(), responses);
    }

    /**
     * @param postNo 
     *   게시글 번호 받아서 해당 게시글의 신고 횟수 추가(Complete)
     */
    @PostMapping("/report/post/{postNo}")
    public Result report(@PathVariable("postNo") int postNo){

        try{
            postService.updateReport(postNo);
            return new Result(true, HttpStatus.OK.value());
        }catch (Exception e){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

}
