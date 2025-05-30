package com.mjc.linkx.user;

import com.mjc.linkx.boardfree.IBoardFree;
import com.mjc.linkx.common.IResponseController;
import com.mjc.linkx.common.dto.CUInfoDto;
import com.mjc.linkx.common.dto.ResponseDto;
import com.mjc.linkx.common.exception.IdNotFoundException;
import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.security.dto.JoinRequest;
import com.mjc.linkx.security.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserRestController implements IResponseController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    private ResponseEntity<ResponseDto> signup(@Valid @RequestBody JoinRequest dto, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                // 에러 메시지를 응답에 포함
                Map<String, String> errorMessages = new HashMap<>();
                bindingResult.getFieldErrors().forEach(error ->
                        errorMessages.put(error.getField(), error.getDefaultMessage())
                );
                return makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "입력 매개변수 에러", errorMessages);
            }
            // 회원가입 : 유저 테이블에 insert
            IUser result = this.userService.join(dto);

            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "성공", result);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null);
        }
    }

    // 로그인
    @PostMapping("/login")
    private ResponseEntity<ResponseDto> login(@RequestBody LoginRequest dto, HttpServletRequest httpServletRequest) {
        try {

            UserDto result = this.userService.login(dto);
            if (result != null) {
                // 세션을 생성하기 전에 기존의 세션 파기
                httpServletRequest.getSession().invalidate();
                HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성
                // 세션에 userId를 넣어줌
                session.setAttribute("LoginUser",result);
                session.setAttribute("userId", result.getId());
                session.setMaxInactiveInterval(7200); // 세션 2시간동안 유지

                return makeResponseEntity(HttpStatus.OK.value(),HttpStatus.OK,"로그인 되었습니다.",result );
            }
            else{
                return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "아이디 또는 비밀번호를 확인해주세요.", null);
            }

        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND, ex.getMessage(), null);
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

    // ID 찾기 (이름, 이메일)
    @PostMapping("/findByNameAndEmail")
    private ResponseEntity<ResponseDto> findByNameAndEmail(@RequestBody JoinRequest joinRequest) {
        if (joinRequest == null) {
            makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "이름과 이메일을 입력해 주세요", null);
        }
        UserDto result = this.userService.findByNameAndEmail(joinRequest.getName(), joinRequest.getEmail());
        if (result != null) {
            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "아이디 찾기에 성공했습니다. 아래를 확인하세요", result.getLoginId());
        }else{
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "내용과 일치하는 회원정보가 없습니다.", null);
        }


    }
    // ID 찾기 (이름, 폰)
    @PostMapping("/findByNameAndPhone")
    private ResponseEntity<ResponseDto> findByNameAndPhone(@RequestBody JoinRequest joinRequest) {
        if (joinRequest == null) {
            makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "이름과 휴대폰번호를 입력해 주세요", null);
        }
        UserDto result = this.userService.findByNameAndPhone(joinRequest.getName(), joinRequest.getPhone());
        if (result != null) {
            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "아이디 찾기에 성공했습니다. 아래를 확인하세요", result.getLoginId());
        }else{
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "내용과 일치하는 회원정보가 없습니다.", null);
        }
    }
    // 비밀번호 찾기 (아이디, 이름, 이메일)
    @PostMapping("/findPassword")
    private ResponseEntity<ResponseDto> findPassword(@RequestBody JoinRequest joinRequest,HttpSession session) {
        if (joinRequest == null) {
            makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "모든 값을 입력해 주세요", null);
        }
        // 로그인ID, 이름, 이메일 전달
        UserDto result = this.userService.findByLoginIdAndNameAndEmail(joinRequest.getLoginId(),joinRequest.getName(), joinRequest.getEmail());
        if (result != null) {
            session.setAttribute("userDto", result);
            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "비밀번호 변경 페이지로 이동합니다.", result);
        }else{
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "내용과 일치하는 회원정보가 없습니다.", null);
        }
    }
    // 비밀번호 변경(아이디, 이름, 이메일)
    @PatchMapping("/changePassword")
    private ResponseEntity<ResponseDto> changePassword(@RequestBody JoinRequest joinRequest,HttpSession session) {
        if (joinRequest == null) {
            makeResponseEntity(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "모든 값을 입력해 주세요", null);
        }
        // 비밀번호 전달
        Boolean result = this.userService.changePassword(joinRequest);
        if (result) {
            return makeResponseEntity(HttpStatus.OK.value(), HttpStatus.OK, "비밀번호가 변경되었습니다.", true);
        }else{
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, "비밀번호 변경 실패", null);
        }
    }
}
