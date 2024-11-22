package com.mjc.linkx.petition;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IPetitionMyBatisMapper {

    void insert(PetitionDto dto);           //청원글 삽입

    PetitionDto findById(Long id);          //청원글 찾기(상세보기)

    void delete(Long id);                   //청원글 삭제

    List<PetitionDto> findAllByNameContains(SearchPetiDto dto);     //검색어 포함 게시글 모두 찾기

    Integer countAllByContains(SearchPetiDto dto);//검색어 포함 게시글 개수 구하기

    void addAgreeQty(Long id);              //동의자 수 증가


    List<PetitionDto> findAll();

    void updatePlaying(Long id, Boolean playing);


    List<PetitionDto> findTopAgreedPetitions();

    void insertSignature(SignatureDto signature);

    boolean hasUserAgreed(SignatureDto signature);
}
