package com.mjc.linkx.user;


import com.mjc.linkx.security.dto.JoinRequest;
import com.mjc.linkx.security.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor

public class UserService {

    private final IUserMybatisMapper userMybatisMapper;

    // 회원가입 시 loginId 중복체크 기능 구현: 중복되면 true 리턴
    public boolean checkLoginIdDuplicate(String loginId) {
        return userMybatisMapper.existByLoginId(loginId);
    }
    // 회원가입 시 nickname 중복체크 기능 구현: 중복되면 true 리턴
    public boolean checkNicknameDuplicate(String nickname) {
        return userMybatisMapper.existByNickname(nickname);
    }
    // 회원가입 시 email 중복체크 기능 구현: 중복되면 true 리턴
    public boolean checkEmailDuplicate(String email) {
        return userMybatisMapper.existByEmail(email);
    }

    // 회원 가입
    public void join(JoinRequest request) {
        userMybatisMapper.insert(request.toUser());
    }

    // 로그인
    public UserDto login(LoginRequest request) {
        UserDto user = userMybatisMapper.findByLoginId(request.getLoginId());

        // 로그인ID와 일치하는 user가 없으면 null 리턴
        if (user == null) {
            return null;
        }
        // user의 비밀번호와 입력된 비밀번호가 다르면 null 리턴
        if (!user.getPassword().equals(request.getPassword())) {
            return null;
        }
        return user;
    }


}
