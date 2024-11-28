// TasteService.java
package com.mjc.linkx.spotlink;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface SpotService {
    String fetchAndSaveTasteData();
    List<SpotDto> getSpotList();

    List<SpotReviewDto> getReviewsBySpotId(Long spotId, HttpSession session);

    void addReview(SpotReviewDto reviewDto);
    void deleteReview(Long reviewId, Long userId) throws Exception;
}
