package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.User.FollowListResponse;
import com.ssafy.challympic.domain.Alert;
import com.ssafy.challympic.domain.Follow;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.FollowRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    private final AlertService alertService;

    @Transactional
    public boolean follow(int srcNo, int destNo){
        try{
            // get은 null일 경우 NoSuchElementException 반환
            Follow follow = followRepository.findBySrcUser_NoAndDestUser_No(srcNo, destNo).get();
            followRepository.delete(follow);
            return false;
        }catch (NoSuchElementException e){
            User srcUser = userRepository.findById(srcNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. "));
            User destUser = userRepository.findById(destNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. "));
            Follow newFollow = Follow.builder()
                    .src_no(srcUser)
                    .dest_no(destUser)
                    .build();
            followRepository.save(newFollow);

            // 팔로우했을때 알림
            User writer = userService.findByNo(destNo);
            User follower = userService.findByNo(srcNo);
            Alert alert = Alert.builder()
                    .user(writer)
                    .content(follower.getNickname() + "님이 팔로우합니다.")
                    .build();
            alertService.saveAlert(alert);

            return true;
        }
    }

    /**
     * userNo가 팔로우 한 사람들
     * @param userNo
     * @return
     */
    public List<FollowListResponse> following(int userNo, int loginUser){
        List<Follow> following = followRepository.findBySrcUser_No(userNo);
        return getFollowListResponses(userNo, loginUser, following);
    }

    /**
     * userNo를 팔로우 한 사람들
     * @param userNo
     * @return
     */
    public List<FollowListResponse> follower(int userNo, int loginUser){
        List<Follow> follower = followRepository.findByDestUser_No(userNo);
        return getFollowListResponses(userNo, loginUser, follower);
    }

    private List<FollowListResponse> getFollowListResponses(int userNo, int loginUser, List<Follow> follower) {
        List<FollowListResponse> followerList = follower.stream()
                .map(f ->{
                    User user = userRepository.findById(f.getDestUser().getNo())
                            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. "));
                    boolean isFollow = followRepository.findBySrcUser_NoAndDestUser_No(loginUser, userNo).isEmpty();
                    return new FollowListResponse(user, !isFollow);
                })
                .collect(Collectors.toList());
        return followerList;
    }

    public boolean isFollow(int srcNo, int destNo) {
        return followRepository.findBySrcUser_NoAndDestUser_No(srcNo, destNo).isEmpty();
    }

    public int followingCnt(int userNo) {
        return followRepository.findBySrcUser_No(userNo).size();
    }

    public int followerCnt(int user_no) {
        return followRepository.findByDestUser_No(user_no).size();
    }
}

