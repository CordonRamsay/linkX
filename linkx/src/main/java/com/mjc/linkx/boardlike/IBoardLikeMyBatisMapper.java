package com.mjc.linkx.boardlike;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBoardLikeMyBatisMapper {
    void insert(BoardLikeDto boardLikeDto);
    void deleteByTypeAndIdAndUser(BoardLikeDto boardLikeDto);
    Integer countByTypeAndIdAndUser(BoardLikeDto boardLikeDto);
}
