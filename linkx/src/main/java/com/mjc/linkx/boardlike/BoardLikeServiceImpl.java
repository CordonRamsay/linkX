package com.mjc.linkx.boardlike;


import com.mjc.linkx.boardfree.BoardFreeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardLikeServiceImpl implements IBoardLikeService{

    private final IBoardLikeMyBatisMapper boardLikeMyBatisMapper;



    // BoardLikeDto의 BoardType, BoardId, UserId로 검색하여 해당하는 좋아요테이블의 데이터 count를 리턴
    // 해당 return값이 0보다 크다면 해당 게시글은 좋아요가 안 된 상태
    @Override
    public Integer countByTypeAndIdAndUser(IBoardLike boardLike) {
        if ( boardLike == null || boardLike.getBoardId() == null
                || boardLike.getCreateId() == null
                || boardLike.getBoardId() == null || boardLike.getBoardId() <= 0 ) {
            return 0;
        }
        BoardLikeDto dto = BoardLikeDto.builder().build();
        dto.copyFields(boardLike);
        Integer count = this.boardLikeMyBatisMapper.countByTypeAndIdAndUser(dto);
        return count;
    }




}
