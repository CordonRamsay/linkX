package com.mjc.linkx.comment;


import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.user.IUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements ICommentService{

    private final ICommentMyBatisMapper commentMyBatisMapper;

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
    public void addLikeQty(Long id) {

    }

    @Override
    public void subLikeQty(Long id) {

    }
}
