package com.mjc.linkx.petition;


import com.mjc.linkx.user.IUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor        //mapper필드에 생성자 주입을 위한 어노테이션
public class PetitionServiceImpl implements IPetitionService{

    //청원 게시판 mapper 객체 변수 선언(생성자 주입)
    private final IPetitionMyBatisMapper petitionMyBatisMapper;

    //원본 코드를 따라서 작성하다가 현재 insert만 따라 하고 나머지는 html작성 후 테스트 진행후 다시 코드 따라하기로 함
    @Override
    public IPetition insert(PetitionDto dto, Long id) {
        if(dto == null){
            return null;
        }

        //원래 코드에도 임시로 id받아오지만 추후 IUser에서 id를 받아오는 형태로 수정할 예정이라고 함
        this.petitionMyBatisMapper.insert(dto);

        return dto;
    }

    @Override
    public IPetition findById(Long id) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public List<PetitionDto> findAllByNameContains(SearchPetiDto dto) {
        if(dto == null){
            return List.of();
        }
        dto.settingValues();
        List<PetitionDto> list = this.petitionMyBatisMapper.findAllByNameContains(dto);

        return list;

    }


    @Override
    public Integer countAllByNameContains(SearchPetiDto dto) {
        return 0;
    }

    @Override
    public void addagreeQty(Long id, IUser user) {

    }
}
