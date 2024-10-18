package com.mjc.linkx.petition;

import com.mjc.linkx.common.dto.SearchDto;

import java.math.BigDecimal;

public class SearchPetiDto extends SearchDto {

    private String petiField;                   //검색할때 참조할 청원 분야
    private Boolean playing;                    //검색할때 참조할 현재 청원이 종료됬는지 확인하는 불값
}
