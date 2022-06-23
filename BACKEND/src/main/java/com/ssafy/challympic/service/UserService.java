package com.ssafy.challympic.service;

import com.ssafy.challympic.api.Dto.User.UserJoinRequest;
import com.ssafy.challympic.api.Dto.User.UserUpdatePwdRequest;
import com.ssafy.challympic.api.Dto.User.UserUpdateRequest;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.repository.UserRepository;
import com.ssafy.challympic.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final MediaService mediaService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * 이메일 중복 감지
     */
    public boolean validateDuplicateEmail(String email){
        if (userRepository.findByEmail(email).isEmpty()) return false;
        return true;
    }

    /**
     * 닉네임 중복 감지
     */
    public boolean validateDuplicateNickname(String nickname){
        if (userRepository.findByNickname(nickname).isEmpty()) return false;
        return true;
    }

    /**
     * 회원가입
     */
    @Transactional
    public int join(UserJoinRequest request){
        return userRepository.save(User.builder()
                .email(request.getUser_email())
                .nickname(request.getUser_nickname())
                .pwd(bCryptPasswordEncoder.encode(request.getUser_pwd()))
                .build()).getNo();
    }


    /**
     * 정보 수정
     */
    @Transactional
    public int updateUser(int no, UserUpdateRequest request){

        User user = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. "));

        // 1. 플로우 시작
        Media media = null;
        MultipartFile files = null;

        try {
            if(request.getFile() != null){
                files = request.getFile();

                // 확장자 체크
                String fileType = getFileType(files);

                // 지원하지 않는 확장자
                if(fileType == null){return 0;}

//            png/jpg, mp4 <- 확장자
//            media = s3Uploader.upload(files, 'image', 'profile');
                media = s3Uploader.upload(files, "image", "profile");

                // AWS S3 업로드 실패
                if(media == null){return 0;}

                int file_no = mediaService.saveMedia(media);

                Media file = mediaService.getMedia(file_no);
                user.update(request.getUser_nickname(), file, request.getUser_title());
            }else{
                user.update(request.getUser_nickname(), null, request.getUser_title());
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return no;
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

        if(extension.equals("AVI"))
            return "VIDEO";

        return null;
    }

    @Transactional
    public int updatePwd(int no, UserUpdatePwdRequest request){
        User user = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        if(!bCryptPasswordEncoder.matches(request.getUser_pwd(), user.getPwd())){
            new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
            return 0;
        }

        user.updatePwd(bCryptPasswordEncoder.encode(request.getUser_newpwd()));

        return no;
    }

    public void deleteUser(int no) {
        User user = userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        userRepository.delete(user);
    }

    public User findByNo(int no){
        return userRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }

    public User findByNickname(String user_nickname){
        return userRepository.findByNickname(user_nickname).get();
    }

    public List<User> findAllUser() {
        return userRepository.findAllUser();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }
}
