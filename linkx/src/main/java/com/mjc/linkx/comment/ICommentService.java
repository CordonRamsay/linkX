package com.mjc.linkx.comment;

import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.user.IUser;

import java.util.List;

public interface ICommentService {
    // 댓글 작성
    IComment insert(IUser user, CommentDto dto);


    // 댓글 조회
    List<CommentDto> findAllByBoardTypeId(SearchCommentDto dto,IUser user);

    // 댓글 상세 조회
    CommentDto findByCommentId(Long id);
    // 댓글 수정
    IComment update(CommentDto dto);

    // 댓글 삭제
    void delete(Long id);

    // 좋아요
    void addLikeQty(Long id);

    // 좋아요 취소
    void subLikeQty(Long id);
}
