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
public class UserRequest implements IUser{


    private Long id;    // id

    private String userId;      // 로그인 시 입력하는 id

    private String userPassword;    // 로그인 시 입력하는 password

    private String userName;    // 이름

    private String userNickname;    // 사용자의 닉네임

    private String userPhone;   // 연락처

    private String userEmail;   // 이메일 : 비밀번호 찾을 때 사용

    private String userUniv;    // 학교명

    private Long majorId;   // 학과-> 콤보박스로 학과를 선택하면 그에맞는 학과id를 보냄

    private String majorName;   // 학과명 -> DeptDto의 deptName 받아옴

    private String userNum; // 학번



}
