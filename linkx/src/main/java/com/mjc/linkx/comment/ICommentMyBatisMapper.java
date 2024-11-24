package com.mjc.linkx.comment;


import com.mjc.linkx.boardcommon.SearchBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ICommentMyBatisMapper {

    // 댓글 작성
    void insert(CommentDto dto);

    // 댓글 조회
    List<CommentDto> findAllByBoardTypeId(SearchBoardDto dto);

    // 댓글 상세 조회
    CommentDto findByCommentId(Long id);

    // 댓글 수정
    void update(CommentDto dto);

    // 댓글 삭제
    void delete(Long id);

    // 댓글 좋아요 수 증가
    void addLikeQty(Long id);
    
    // 댓글 좋아요 수 감소
    void subLikeQty(Long id);
    
    


}
