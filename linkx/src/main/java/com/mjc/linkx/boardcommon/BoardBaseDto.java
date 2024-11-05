package com.mjc.linkx.boardcommon;


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
public class BoardBaseDto implements IBoardBase{

    //작성자 id
    Long createId;
    //작성일
    String createDt;
    //작성자 이름
    String createName;
    //수정일
    String updateDt;
    //삭제 여부
    Boolean deleteYn;
    //테이블명
    String boardType;

}
