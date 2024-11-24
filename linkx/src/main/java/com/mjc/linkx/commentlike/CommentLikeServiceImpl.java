package com.mjc.linkx.commentlike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl implements ICommentLikeService {
    @Autowired
    private ICommentLikeMybatisMapper commentLikeMybatisMapper;

    
    // 유저가 한 댓글에 좋아요를 했는지 안했는지 체크하는 메소드
    @Override
    public Integer countByCommentIdAndUser(ICommentLike searchDto) {
        if ( searchDto == null || searchDto.getCommentId() == null
                || searchDto.getCreateId() == null
                || searchDto.getCommentId() == null || searchDto.getCommentId() <= 0 ) {
            return 0;
        }
        CommentLikeDto search = CommentLikeDto.builder().build();
        search.copyFields(searchDto);
        Integer count = this.commentLikeMybatisMapper.countByCommentIdAndUser(search);
        return count;
    }
}
