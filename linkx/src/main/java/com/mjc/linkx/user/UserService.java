package com.mjc.linkx.user;


import com.mjc.linkx.security.config.MjPasswordEncoder;

import com.mjc.linkx.security.dto.JoinRequest;
import com.mjc.linkx.security.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor

public class UserService {

    private final IUserMybatisMapper userMybatisMapper;
    private final PasswordEncoder encoder;


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

        request.setPassword(encoder.encode(request.getPassword()));
        userMybatisMapper.insert(request.toUser());
    }

    // 로그인
    public UserDto login(LoginRequest request) {

        if(request == null || request.getLoginId() == null || request.getPassword() == null) {
            return null;
        }
        UserDto user = userMybatisMapper.findByLoginId(request.getLoginId());

        // 로그인ID와 일치하는 user가 없으면 null 리턴
        if (user == null) {
            return null;
        }
        // 인코딩된 비밀번호와 입력된 비밀번호를 비교
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return null;
        }
        return user;
    }

    // 정보 수정
    public void update(UserDto dto) {
        if (dto == null) {
            return;
        }
        this.userMybatisMapper.update(dto);

    }

    // 사용자 삭제
    public void delete(Long id) {
        if (id == null || id < 0) {
            return;
        }
        this.userMybatisMapper.delete(id);
    }

    // ID 찾기
    public UserDto findByNameAndEmail(String name, String email) {
        if (name == null || email == null) {
            return null;
        }
        UserDto find = this.userMybatisMapper.findByNameAndEmail(name, email);
        return find;
    }

    // PW 찾기
    public UserDto findByLoginIdAndNameAndEmail(String loginId, String name, String email) {
        if (loginId == null || name == null || email == null) {
            return null;
        }
        UserDto find = this.userMybatisMapper.findByLoginIdAndNameAndEmail(loginId, name, email);
        return find;
    }
    public UserDto getLoginUserByLoginId(String loginId) {
        if (loginId == null) {
            return null; // loginId가 없으면 null 반환
        }

        UserDto user = userMybatisMapper.findByLoginId(loginId);
        if (user == null) {
            return null; // 사용자 정보가 없으면 null 반환
        }

        return user;
    }

    public UserDto getLoginUserById(Long id) {
        if (id == null) {
            return null;
        }
        UserDto user = userMybatisMapper.findById(id);

        if (user == null) {
            return null;
        }
        return user;
    }


}
