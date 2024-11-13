package com.mjc.linkx.petition;

import com.mjc.linkx.common.dto.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SearchPetiDto extends SearchDto {

    private String petiField;                   //검색할때 참조할 청원 분야
    private Boolean playing;                    //검색할때 참조할 현재 청원이 종료됬는지 확인하는 불값
}
