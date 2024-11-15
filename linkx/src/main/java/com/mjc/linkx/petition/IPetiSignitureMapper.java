package com.mjc.linkx.petition;

public interface IPetiSignitureMapper {
    void insert(PetiSignitureDto petiSignitureDto);
    Integer countByIdAndUser(PetiSignitureDto petiSignitureDto);

}
