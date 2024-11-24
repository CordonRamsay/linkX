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
        dto.setUserId(id);
        this.petitionMyBatisMapper.insert(dto);
        return dto;
    }

    @Override
    public IPetition findById(Long id) {
        if(id == null || id < 0){
            return null;
        }
       IPetition petition = this.petitionMyBatisMapper.findById(id);
        return petition;

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
    //findAll구현 안돼어 있어서 구현해야 함
    public List<PetitionDto> findAll(){
        return petitionMyBatisMapper.findAll();
    }


    // playing 상태 업데이트 메서드
    public void updatePlaying(Long id, Boolean playing) {
        petitionMyBatisMapper.updatePlaying(id, playing);
    }


}
