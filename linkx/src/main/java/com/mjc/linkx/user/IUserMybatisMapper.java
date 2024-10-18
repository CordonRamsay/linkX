package com.mjc.linkx.user;

import com.mjc.linkx.common.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserMybatisMapper{
    UserRequest findByLoginId(String loginId);
    UserRequest findByNickname(String nickname);
    void changePassword(UserRequest dto);
    UserRequest findByEmail(String email);
    UserRequest findByName(String name);
    Integer countAllByNameContains(SearchDto search);
    List<UserRequest> findAllByNameContains(SearchDto search);
    int idCheck(String loginId);
    int emailCheck(String email);
    int nicknameCheck(String nickname);
}
