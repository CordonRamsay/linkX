package com.mjc.linkx.comment;

import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.user.IUser;

import java.util.List;

public interface ICommentService {
    // 댓글 작성
    IComment insert(IUser user, CommentDto dto);

    //댓글 개수
    Integer countAllByBoardId(SearchBoardDto dto);

    // 댓글 조회
    List<CommentDto> findAllByBoardTypeId(SearchBoardDto dto,IUser user);

    // 댓글 수정
    void update(CommentDto dto);

    // 댓글 삭제
    void delete(Long id);

    // 좋아요
    void addLikeQty(Long id);

    // 좋아요 취소
    void subLikeQty(Long id);
}
