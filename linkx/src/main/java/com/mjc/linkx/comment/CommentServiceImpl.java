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
    public void update(CommentDto dto) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void addLikeQty(Long id) {

    }

    @Override
    public void subLikeQty(Long id) {

    }
}
