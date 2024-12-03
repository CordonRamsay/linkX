package com.mjc.linkx.spotlink;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ISpotMyBatisMapper {
    void insert(SpotDto spotDto);
    List<SpotDto> getSpotList();
    List<SpotReviewDto> getReviewsBySpotId(Long spotId);
    void insertReview(SpotReviewDto reviewDto);
    void deleteReviewById(Long reviewId);
    SpotReviewDto getReviewById(Long reviewId);
    SpotDto getSpotById(Long spotId);

    List<SpotDto> getTopSpots();
}
