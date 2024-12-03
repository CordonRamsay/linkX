package com.mjc.linkx.boarddept;


import com.mjc.linkx.boardcommon.BoardBaseDto;
import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.user.IUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBoardDeptService {


    IBoardDept insert(BoardDeptDto dto,IUser user);

    IBoardDept update(BoardDeptDto dto);

    IBoardDept findById(Long id);

    Boolean delete(Long id);

    List<BoardDeptDto> findAllByNameContains(SearchBoardDto dto);

    List<BoardDeptDto> findRecently(); // 상위 5개글 찾아오기

    Integer countAllByNameContains(SearchBoardDto dto);

    void addViewQty(Long id,IUser user) throws Exception;

    void addLikeQty(Long id,IUser user) throws Exception;

    void subLikeQty(Long id,IUser user) throws Exception;
}
