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
    //삭제 여부
    Boolean deleteYn;

    //수정일
    String updateDt;

    //테이블명
    String boardType;
    //조회수
    private Integer viewQty;
    //좋아요 수
    private Integer likeQty;

}
