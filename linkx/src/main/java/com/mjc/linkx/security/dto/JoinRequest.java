package com.mjc.linkx.security.dto;


import com.mjc.linkx.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class JoinRequest {

    @NotBlank(message = "로그인 아이디가 비어있습니다.")
    private String loginId;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;
    private String passwordCheck;

    @NotBlank(message = "이름이 비어있습니다.")
    private String name;

    @NotBlank(message = "닉네임이 비어있습니다.")
    private String nickname;

    @NotBlank(message = "전화번호가 비어있습니다.")
    private String phone;

    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;

    @NotBlank(message = "학교명이 비어있습니다.")
    private String univ;

    @NotNull
    private Long majorId;

    @NotBlank(message = "학번이 비어있습니다.")
    private String stuNum;

    // 비밀번호 암호화 X
    public UserDto toUser() {
        return UserDto.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .nickname(this.nickname)
                .phone(this.phone)
                .email(this.email)
                .univ(this.univ)
                .majorId(this.majorId)
                .stuNum(this.stuNum)
                .build();
    }

}
