package com.mjc.linkx.comment;


import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.commentlike.CommentLikeDto;
import com.mjc.linkx.commentlike.ICommentLikeMybatisMapper;
import com.mjc.linkx.commentlike.ICommentLikeService;
import com.mjc.linkx.user.IUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements ICommentService{

    private final ICommentMyBatisMapper commentMyBatisMapper;
    private final ICommentLikeMybatisMapper commentLikeMybatisMapper;

    @Override
    public IComment insert(IUser user,CommentDto dto) {
        if (user == null) {
            return null;
        }
        if (dto == null) {
            return null;
        }

        dto.setCreateId(user.getId());
        this.commentMyBatisMapper.insert(dto);

        return dto;
    }


    @Override
    public List<CommentDto> findAllByBoardTypeId(SearchCommentDto dto,IUser user) {
        if (dto == null) {
            return null;
        }
        dto.settingValues();
        dto.setFirstIndex(dto.getFirstIndex());
        List<CommentDto> list = this.commentMyBatisMapper.findAllByBoardTypeId(dto);

        // 좋아요 여부 설정
        list.forEach(comment -> {
            CommentLikeDto searchDto = CommentLikeDto.builder()
                    .commentId(comment.getId())
                    .createId(user.getId())
                    .build();
            Integer count = commentLikeMybatisMapper.countByCommentIdAndUser(searchDto);
            comment.setLikeYn(count > 0); // CommentDto에 likeYn 값 설정
        });
        return list;
    }

    @Override
    public CommentDto findByCommentId(Long id){
        if (id == null) {
            return null;
        }
        CommentDto find = this.commentMyBatisMapper.findByCommentId(id);
        return find;
    }
    @Override
    public IComment update(CommentDto dto) {
        if (dto == null) {
            return null;
        }
        dto.setUpdateInfo();
        this.commentMyBatisMapper.update(dto);

        return dto;

    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            return;
        }
        this.commentMyBatisMapper.delete(id);
    }

    @Override
    public void commentLike(Long id,IUser user) {
        if (id == null) {
            return;
        }
        CommentLikeDto commentLikeDto = CommentLikeDto.builder()
                .commentId(id)
                .createId(user.getId())
                .build();
        // 댓글 좋아요 행 삽입
        this.commentLikeMybatisMapper.insert(commentLikeDto);
        // 댓글 게시판 좋아요 수 증가
        this.commentMyBatisMapper.addLikeQty(id);
    }

    @Override
    public void commentSubLike(Long id,IUser user) {
        if (id == null) {
            return;
        }
        CommentLikeDto commentLikeDto = CommentLikeDto.builder()
                .commentId(id)
                .createId(user.getId())
                .build();
        // 댓글 좋아요 행 삭제
        this.commentLikeMybatisMapper.delete(commentLikeDto);
        // 댓글 게시판 좋아요 수 감소
        this.commentMyBatisMapper.subLikeQty(id);
    }
}
