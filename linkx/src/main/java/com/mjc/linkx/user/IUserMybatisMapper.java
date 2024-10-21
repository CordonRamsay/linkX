package com.mjc.linkx.user;

import com.mjc.linkx.common.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserMybatisMapper{

    // 닉네임으로 사용자 존재 여부 확인 (중복 체크)
    boolean existByNickname(String nickname);

    // 로그인 ID로 사용자 존재 여부 확인 (중복 체크)
    boolean existByLoginId(String loginId);

    // 이메일로 사용자 존재 여부 확인 (중복 체크)
    boolean existByEmail(String email);

    // 회원가입
    void insert(UserDto user);

    // 로그인ID로 사용자 조회 ( 로그인 )
    UserDto findByLoginId(String loginId);

    UserDto findById(Long id);
    // 사용자 정보 수정
    void update(UserDto user);

    // 사용자 삭제
    void delete(Long id);



    // 이름과 이메일로 사용자 정보 조회 (ID 찾기 )
    UserDto findByNameAndEmail(String name, String email);

    // 로그인ID, 이름, 이메일로 사용자 정보 조회 ( 비밀번호 찾기 )
    UserDto findByLoginIdAndNameAndEmail(String loginId, String name, String email);

}
