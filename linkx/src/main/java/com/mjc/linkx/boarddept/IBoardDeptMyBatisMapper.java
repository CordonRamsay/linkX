package com.mjc.linkx.boarddept;


import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.boardfree.BoardFreeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardDeptMyBatisMapper {

    void insert(BoardDeptDto dto);  // 게시글 삽입

    BoardDeptDto findById(Long id); // 게시글 찾기(상세보기)

    void update(BoardDeptDto dto);  // 게시글 수정

    void delete(Long id); // 게시글 삭제

    List<BoardDeptDto> findAllByNameContains(SearchBoardDto dto); //검색어포함 게시글 모두 찾기

    List<BoardDeptDto> findRecently(); // 상위 5개글 찾아오기
    List<BoardDeptDto> findViewTop(); // 상위 5개글 찾아오기
    Integer countAllByNameContains(SearchBoardDto dto); //검색어포함 게시글 개수 구하기

    void addViewQty(Long id);  // 조회수 증가

    void addLikeQty(Long id);   // 좋아요 수 증가

    void subLikeQty(Long id);   // 좋아요 수 감소






}
