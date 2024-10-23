package com.mjc.linkx.boardlike;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBoardLikeMyBatisMapper {
    void insert(BoardLikeDto dto);
    void update(BoardLikeDto dto);
    void delete(BoardLikeDto dto);
    void deleteById(Long id);
    BoardLikeDto findById(Long id);

    void deleteByTableUserBoard(BoardLikeDto boardLikeDto);
    Integer countByTypeAndIdAndUser(BoardLikeDto boardLikeDto);
}
