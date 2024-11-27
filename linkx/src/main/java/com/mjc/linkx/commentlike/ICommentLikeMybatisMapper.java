package com.mjc.linkx.commentlike;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICommentLikeMybatisMapper {
    void insert(CommentLikeDto dto);
    void delete(CommentLikeDto dto);
    CommentLikeDto findById(Long id);
    Integer countByCommentIdAndUser(CommentLikeDto dto);
}
