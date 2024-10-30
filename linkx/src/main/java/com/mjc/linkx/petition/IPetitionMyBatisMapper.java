package com.mjc.linkx.petition;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPetitionMyBatisMapper {

    void insert(PetitionDto dto);

    PetitionDto findById(Long id);

    void delete(Long id);
}
