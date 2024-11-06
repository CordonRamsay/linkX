package com.mjc.linkx.taste;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ITasteRestMyBatisMapper {
    void insert(TasteRestDto tasteRestDto);
    List<TasteRestDto> setMapMaker();
    //List<TasteRestDto> getAllRestaurants();
}
