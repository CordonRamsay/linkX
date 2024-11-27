package com.mjc.linkx.user;

import com.mjc.linkx.boardfree.IBoardFree;
import com.mjc.linkx.common.IResponseController;
import com.mjc.linkx.common.dto.CUInfoDto;
import com.mjc.linkx.common.dto.ResponseDto;
import com.mjc.linkx.common.exception.IdNotFoundException;
import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.security.dto.JoinRequest;
import com.mjc.linkx.security.dto.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/vi/user")
public class UserRestController implements IResponseController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    private ResponseEntity<ResponseDto> signup(@Valid @RequestBody JoinRequest dto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                // 유효성 검사 실패 시 오류 메시지 추출
                String errorMessages = bindingResult.getFieldErrors().stream()
                        .map(error -> String.format("'%s': %s", error.getField(), error.getDefaultMessage()))
                        .reduce((msg1, msg2) -> msg1 + ", " + msg2) // 여러 메시지 결합
                        .orElse("Invalid input.");

                return makeResponseEntity(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST,
                        errorMessages,
                        null
                );
            }
            if (dto == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", null);
            }
            // 회원가입 : user테이블에 insert
            IUser result = this.userService.join(dto);

            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    }

    // ID 중복 검사
    @PostMapping("/checkId")
    private ResponseEntity<ResponseDto> checkId(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            if (loginRequest == null) {
                makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "아이디를 입력해 주세요", null);
            }

            Boolean result = this.userService.checkLoginIdDuplicate(loginRequest.getLoginId());
            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", result);

        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }

    }
    // 닉네임 중복 검사
    @PostMapping("/checkNickname")
    private ResponseEntity<ResponseDto> checkNickname(@RequestBody JoinRequest joinRequest) {
        if (joinRequest == null) {
            makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "닉네임을 입력해 주세요", null);
        }
        // result가 true면 중복, false면 사용가능
        Boolean result = this.userService.checkNicknameDuplicate(joinRequest.getNickname());

        return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", result);
    }
    // 이메일 중복 검사
    @PostMapping("/checkEmail")
    private ResponseEntity<ResponseDto> checkEmail(@RequestBody JoinRequest joinRequest) {
        if (joinRequest == null) {
            makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "이메일을 입력해 주세요", null);
        }

        // result가 true면 중복, false면 사용가능
        Boolean result = this.userService.checkEmailDuplicate(joinRequest.getEmail());

        return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", result);
    }
    // 학번 중복 검사
    @PostMapping("/checkStuNum")
    private ResponseEntity<ResponseDto> checkStuNum(@RequestBody JoinRequest joinRequest) {
        if (joinRequest == null) {
            makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "학번을 입력해 주세요", null);
        }
        // result가 true면 중복, false면 사용가능
        Boolean result = this.userService.checkStuNumDuplicate(joinRequest.getStuNum());

        return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", result);
    }
}
