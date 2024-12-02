package com.mjc.linkx.boardfree;


import com.mjc.linkx.boardcommon.SearchBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardFreeMyBatisMapper {

    void insert(BoardFreeDto dto);  // 게시글 삽입

    BoardFreeDto findById(Long id); // 게시글 찾기(상세보기)

    void update(BoardFreeDto dto);  // 게시글 수정

    void delete(Long id); // 게시글 삭제

    List<BoardFreeDto> findAllByNameContains(SearchBoardDto dto); //검색어포함 게시글 모두 찾기

    List<BoardFreeDto> findRecently(); // 상위 5개글 찾아오기
    List<BoardFreeDto> findViewTop(); // 상위 5개글 찾아오기

    Integer countAllByNameContains(SearchBoardDto dto); //검색어포함 게시글 개수 구하기

    void addViewQty(Long id);  // 조회수 증가

    void addLikeQty(Long id);   // 좋아요 수 증가

    void subLikeQty(Long id);   // 좋아요 수 감소






}
