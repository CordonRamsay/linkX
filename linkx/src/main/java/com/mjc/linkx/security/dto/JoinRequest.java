package com.mjc.linkx.security.dto;


import com.mjc.linkx.user.IUser;
import com.mjc.linkx.user.UserDto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

public class JoinRequest implements IUser {

    private Long id;

    @Size(min = 6, max = 20, message = "아이디는 6 ~ 20자 이내로 입력해주세요.")
    @NotEmpty(message = "아이디를 입력해주세요")
    private String loginId;

    @Size(min = 6, max = 20, message = "비밀번호는 6 ~ 20자 이내로 입력해주세요.")
    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String passwordCheck;

    @NotEmpty(message = "회원 이름을 입력해주세요")
    private String name;

    @Size(min = 2, max = 8, message = "닉네임은 2 ~ 8자 이내로 입력해주세요.")
    @NotEmpty(message = "닉네임을 입력해주세요")
    private String nickname;

    @Size(min = 11, max = 11, message = "전화번호 형식이 올바르지 않습니다.")
    @NotEmpty(message = "전화번호를 입력해주세요")
    private String phone;

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @NotEmpty(message = "학교명을 입력해주세요")
    private String univ;

    @Size(min = 10, max = 10, message = "학번은 10자여야 합니다")
    @NotEmpty(message = "학번을 입력해주세요")
    private String stuNum;

    @NotNull(message = "전공을 선택해주세요")
    private Long majorId;



    private String majorName;

    private Boolean active;

    private UserRole role;


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
