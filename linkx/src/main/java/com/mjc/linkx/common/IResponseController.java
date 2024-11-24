package com.mjc.linkx.common;


import com.mjc.linkx.boardcommon.IBoardBase;
import com.mjc.linkx.common.dto.CUInfoDto;
import com.mjc.linkx.common.dto.ResponseCode;
import com.mjc.linkx.common.dto.ResponseDto;
import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.user.IUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface IResponseController {

    // user 객체가 null 인지 확인하는 예외처리
    default CUInfoDto makeResponseCheckLogin(IUser user) {

        if (user == null) {
            throw new LoginAccessException("로그인 필요");
        }
        return new CUInfoDto(user);
    }

    // session 에서 "LoginUser" 키를 가진 로그인 유저 객체 가져옴
    default CUInfoDto makeResponseCheckLogin(HttpSession session) {
        IUser loginUser = (IUser) session.getAttribute("LoginUser");
        if (loginUser == null) {
            throw new LoginAccessException("로그인 필요");
        }
        return new CUInfoDto(loginUser);
    }

    // session 에서  "LoginUser" 키를 가진 로그인 유저 객체의 id와 게시글 작성자 id를 비교
    default CUInfoDto makeResponseCheckSelfOrAdmin(HttpSession session, IBoardBase checkObject) {
        IUser loginUser = (IUser) session.getAttribute("LoginUser");
        if (loginUser == null) {
            throw new LoginAccessException("로그인 필요");
        } else if (!loginUser.getId().equals(checkObject.getCreateId())) {
            throw new LoginAccessException("본인만 가능");
        }
        return new CUInfoDto(loginUser);
    }

    // HTTP 응답 리턴 ( 상태코드, 메시지, ResponseDto(데이터) )
    default ResponseEntity<ResponseDto> makeResponseEntity(Integer code, HttpStatus httpStatus
            , String message
            , Object responseData) {
        ResponseDto dto = ResponseDto.builder()
                .code(code)
                .httpStatus(httpStatus)
                .message(message)
                .responseData(responseData)
                .build();
        return ResponseEntity.status(httpStatus).body(dto);
    }
}
