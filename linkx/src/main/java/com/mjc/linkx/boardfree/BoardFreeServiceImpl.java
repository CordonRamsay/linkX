package com.mjc.linkx.boardfree;


import com.mjc.linkx.boardlike.BoardLikeDto;
import com.mjc.linkx.boardlike.IBoardLikeMyBatisMapper;
import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.user.IUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor   // mapper 필드에 생성자 주입을 위한 어노테이션

public class BoardFreeServiceImpl implements IBoardFreeService{


    // 자유게시판 mapper 객체 변수 선언(생성자 주입)
    private final IBoardFreeMyBatisMapper boardMyBatisMapper;
    // 게시판좋아요 mapper 객체 변수 선언(생성자 주입)
    private final IBoardLikeMyBatisMapper boardLikeMyBatisMapper;

    @Override
    public IBoardFree insert(BoardFreeDto dto,Long id) {

        if (dto == null) {
            return null;
        }

        dto.setCreateId(id);  // 임시 -> 나중에 로그인정보받아온 IUser의 id 넣어주기
        this.boardMyBatisMapper.insert(dto);

        return dto;
    }

    @Override
    public IBoardFree findById(Long id) {
        if (id == null || id < 0) {
            return null;
        }
        IBoardFree find = this.boardMyBatisMapper.findById(id);

        return find;


    }

    @Override
    public IBoardFree update(BoardFreeDto dto) {
        if (dto == null) {
            return null;
        }
        dto.setUpdateInfo();   // dto의 updateDt에 수정일 삽입
        this.boardMyBatisMapper.update(dto);

        return dto;
    }

    @Override
    public Boolean delete(Long id) {
        if (id == null || id < 0) {
            return false;
        }
        this.boardMyBatisMapper.delete(id);

        return true;
    }

    @Override
    public List<BoardFreeDto> findAllByNameContains(SearchBoardDto dto) {
        if (dto == null) {
            return List.of();
        }
        dto.settingValues();
        List<BoardFreeDto> list = this.boardMyBatisMapper.findAllByNameContains(dto);

        return list;
    }

    @Override
    public Integer countAllByNameContains(SearchBoardDto dto) {
        if (dto == null) {
            return null;
        }
        dto.settingValues();
        Integer count = this.boardMyBatisMapper.countAllByNameContains(dto);

        return count;
    }

    @Override
    public void addViewQty(Long id,IUser user) {
        if (id == null || id < 0) {
            return;
        }
        IBoardFree find = this.findById(id);
        if (find.getCreateId().equals(user.getId())) {
            return;
        }
        // 자유 게시판 테이블에 조회 수 증가
        this.boardMyBatisMapper.addViewQty(id);
    }

    @Override
    public void addLikeQty(Long id, IUser user) {
        if (id == null || id < 0 || user == null) {
            return;
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .createId(user.getId())
                .boardId(id)
                .boardType(new BoardFreeDto().getBoardType())
                .build();

        Integer count = this.boardLikeMyBatisMapper.countByTypeAndIdAndUser(boardLikeDto);
        if (count > 0) {
            return;
        }
        //좋아요 테이블에 행 삽입
        this.boardLikeMyBatisMapper.insert(boardLikeDto);

        // 자유 게시판 테이블에 좋아요 수 증가
        this.boardMyBatisMapper.addLikeQty(id);
    }

    @Override
    public void subLikeQty(Long id,IUser user) {
        if (id == null || id < 0 || user == null) {
            return;
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .createId(user.getId())
                .boardType(new BoardFreeDto().getBoardType())
                .boardId(id)
                .build();
        // 좋아요 테이블에 데이터 삭제
        this.boardLikeMyBatisMapper.deleteByTypeAndIdAndUser(boardLikeDto);
        // 자유 게시판 테이블에 좋아요 수 감소
        this.boardMyBatisMapper.subLikeQty(id);
    }
}
