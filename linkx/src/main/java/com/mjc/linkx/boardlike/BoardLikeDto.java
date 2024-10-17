package com.mjc.linkx.boardlike;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class BoardLikeDto implements IBoardLike{
    // id
    private Long id;
    // 게시판 종류 ( 자유, 학과 )
    private String boardType;
    // 게시판 id
    private Long boardId;
    // 작성자 id - > UserDto의 id로 받아옴
    private Long createId;

}
