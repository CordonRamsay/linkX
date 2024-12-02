package com.mjc.linkx.boardfree;

import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.user.IUser;

import java.util.List;

public interface IBoardFreeService {

    IBoardFree insert(BoardFreeDto dto,IUser user);

    IBoardFree findById(Long id);

    IBoardFree update(BoardFreeDto dto);

    Boolean delete(Long id);

    List<BoardFreeDto> findAllByNameContains(SearchBoardDto dto);
    List<BoardFreeDto> findRecently();
    List<BoardFreeDto> findViewTop();

    Integer countAllByNameContains(SearchBoardDto dto);

    void addViewQty(Long id,IUser user);

    void addLikeQty(Long id,IUser user);

    void subLikeQty(Long id,IUser user);
}
