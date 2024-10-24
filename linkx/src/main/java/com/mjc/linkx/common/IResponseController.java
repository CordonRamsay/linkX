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
    default CUInfoDto makeResponseCheckLogin(IUser user) {

        if (user == null) {
            throw new LoginAccessException("로그인 필요");
        }
        return new CUInfoDto(user);
    }

    default CUInfoDto makeResponseCheckLoginAdmin(HttpSession session) {
        IUser loginUser = (IUser) session.getAttribute("LoginUser");
        if (loginUser == null) {
            throw new LoginAccessException("로그인 필요");
        }
        return new CUInfoDto(loginUser);
    }

    default CUInfoDto makeResponseCheckSelfOrAdmin(HttpSession session, IBoardBase checkObject) {
        IUser loginUser = (IUser) session.getAttribute("LoginUser");
        if (loginUser == null) {
            throw new LoginAccessException("로그인 필요");
        } else if (!loginUser.getId().equals(checkObject.getCreateId())) {
            throw new LoginAccessException("본인만 가능");
        }
        return new CUInfoDto(loginUser);
    }

    default ResponseEntity<ResponseDto> makeResponseEntity(HttpStatus httpStatus
            , ResponseCode responseCode
            , String message
            , Object responseData) {
        ResponseDto dto = ResponseDto.builder()
                .responseCode(responseCode)
                .message(message)
                .responseData(responseData)
                .build();
        return ResponseEntity.status(httpStatus).body(dto);
    }
}
