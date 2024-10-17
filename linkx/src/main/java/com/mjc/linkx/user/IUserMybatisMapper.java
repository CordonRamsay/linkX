package com.mjc.linkx.user;

import com.mjc.linkx.common.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserMybatisMapper{
    UserDto findByLoginId(String loginId);
    UserDto findByNickname(String nickname);
    void changePassword(UserDto dto);
    UserDto findByEmail(String email);
    UserDto findByName(String name);
    Integer countAllByNameContains(SearchDto search);
    List<UserDto> findAllByNameContains(SearchDto search);
    int idCheck(String loginId);
    int emailCheck(String email);
    int nicknameCheck(String nickname);
}
