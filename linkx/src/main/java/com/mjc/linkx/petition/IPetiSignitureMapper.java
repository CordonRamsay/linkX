package com.mjc.linkx.petition;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IPetiSignitureMapper {
    void insert(PetiSignitureDto petiSignitureDto);
    Integer countByIdAndUser(PetiSignitureDto petiSignitureDto);

}
