package com.mjc.linkx.taste;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ITasteRestMyBatisMapper {
    void insert(TasteRestDto tasteRestDto);
    List<TasteRestDto> getTasteList();

    List<TasteReviewDto> getReviewsByRestaurantId(Long restId);

    void insertReview(TasteReviewDto reviewDto);

    void deleteReviewById(Long reviewId);

    TasteReviewDto getReviewById(Long reviewId);
    //List<TasteRestDto> getAllRestaurants();
}
