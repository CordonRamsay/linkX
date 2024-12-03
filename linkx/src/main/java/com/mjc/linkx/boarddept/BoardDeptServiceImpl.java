package com.mjc.linkx.boarddept;


import com.mjc.linkx.boardcommon.SearchBoardDto;
import com.mjc.linkx.boardfree.BoardFreeDto;
import com.mjc.linkx.boardlike.BoardLikeDto;
import com.mjc.linkx.boardlike.IBoardLikeMyBatisMapper;
import com.mjc.linkx.user.IUser;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor   // mapper 필드에 생성자 주입을 위한 어노테이션

public class BoardDeptServiceImpl implements IBoardDeptService {


    // 학과게시판 mapper 객체 변수 선언(생성자 주입)
    private final IBoardDeptMyBatisMapper boardMyBatisMapper;
    // 게시판좋아요 mapper 객체 변수 선언(생성자 주입)
    private final IBoardLikeMyBatisMapper boardLikeMyBatisMapper;

  
    @Override
    public IBoardDept insert(BoardDeptDto dto,IUser user) {
        if (dto == null) {
            return null;
        }

        dto.setCreateId(user.getId());
        this.boardMyBatisMapper.insert(dto);

        return dto;
    }


    @Override
    public IBoardDept findById(Long id) {
        if (id == null || id < 0) {
            return null;
        }
        IBoardDept find = this.boardMyBatisMapper.findById(id);

        return find;


    }

    @Override
    public IBoardDept update(BoardDeptDto dto) {
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
    public List<BoardDeptDto> findAllByNameContains(SearchBoardDto dto) {
        if (dto == null) {
            return List.of();
        }
        dto.settingValues();

        List<BoardDeptDto> list = this.boardMyBatisMapper.findAllByNameContains(dto);

        return list;
    }

    @Override
    public List<BoardDeptDto> findRecently() {

        List<BoardDeptDto> list = this.boardMyBatisMapper.findRecently();

        // HTML 태그 제거
        for (BoardDeptDto dto : list) {
            String plainText = Jsoup.parse(dto.getContent()).text(); // HTML 파싱 후 텍스트 추출
            // 내용이 10자를 넘으면 '...' 추가
            if (plainText.length() > 10) {
                plainText = plainText.substring(0, 10) + "...";
            }

            dto.setContent(plainText); // 텍스트를 다시 설정
        }
        return list;
    }

    @Override
    public List<BoardDeptDto> findViewTop() {
        List<BoardDeptDto> list = this.boardMyBatisMapper.findViewTop();

        // HTML 태그 제거
        for (BoardDeptDto dto : list) {
            String plainText = Jsoup.parse(dto.getContent()).text(); // HTML 파싱 후 텍스트 추출
            // 내용이 10자를 넘으면 '...' 추가
            if (plainText.length() > 10) {
                plainText = plainText.substring(0, 10) + "...";
            }

            dto.setContent(plainText); // 텍스트를 다시 설정
        }
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
        IBoardDept find = this.findById(id);
        if (find.getCreateId().equals(user.getId())) {
            return;
        }
        // 자유 게시판 테이블에 조회 수 증가
        this.boardMyBatisMapper.addViewQty(id);
    }

    @Override
    public void addLikeQty(Long id,IUser user) {
        if (id == null || id < 0 || user == null) {
            return;
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .createId(user.getId())
                .boardType(new BoardDeptDto().getBoardType())
                .boardId(id)
                .build();
        Integer count = this.boardLikeMyBatisMapper.countByTypeAndIdAndUser(boardLikeDto);
        if (count > 0) {
            return;
        }

        // 좋아요 테이블에 데이터 삽입
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
                .boardType(new BoardDeptDto().getBoardType())
                .boardId(id)
                .build();
        // 좋아요 테이블에 데이터 삽입
        this.boardLikeMyBatisMapper.deleteByTypeAndIdAndUser(boardLikeDto);
        // 자유 게시판 테이블에 좋아요 수 증가
        this.boardMyBatisMapper.subLikeQty(id);
    }
}
