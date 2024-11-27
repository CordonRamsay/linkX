package com.mjc.linkx.commentlike;


public interface ICommentLikeService {

    Integer countByCommentIdAndUser(ICommentLike searchDto);
}
