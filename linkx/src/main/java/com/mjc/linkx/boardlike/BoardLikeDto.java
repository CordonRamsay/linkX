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

// boardId는 좋아요를 누를 때 보낸 BoardDto의 id를 받아오고
// boardType은 좋아요를 누를때 어떻게받아올까? ( 각각의 BoardDto에서 getter를 만든다 / 누를 때 쿼리스트링으로 넣어준다 )
public class BoardLikeDto implements IBoardLike{
    // id
    private Long id;

    // 게시판 종류 ( 어느게시판에 좋아요를 했는지  [자유,학과])
    private String boardType;

    // 게시판 id ( 몇번 게시글에 좋아요를 했는지 )
    private Long boardId;

    // 작성자 id - > UserDto의 id로 받아옴
    private Long createId;

}
