package com.mjc.linkx.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements IUser{

    // id
    private Long id;
    // 로그인 시 입력하는 id
    private String userId;
    // 로그인 시 입력하는 password
    private String userPassword;
    // 이름
    private String userName;
    // 사용자의 닉네임
    private String userNickname;
    // 연락처
    private String userPhone;
    // 이메일 : 비밀번호 찾을 때 사용
    private String userEmail;
    // 학교명
    private String userUniv;
    // 학과-> DeptDto의 id 받아옴
    private Long majorId;
    // 학과명 -> DeptDto의 deptName 받아옴
    private String majorName;
    // 학번
    private String userNum;



}
