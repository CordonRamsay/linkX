package com.mjc.linkx.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    // http 상태코드, http 상태, 메세지, 응답데이터
    private Integer code;
    private HttpStatus httpStatus;
    private String message;
    private Object responseData;
}
