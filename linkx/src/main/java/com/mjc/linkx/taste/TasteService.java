// TasteService.java
package com.mjc.linkx.taste;

import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface TasteService {
    String fetchAndSaveTasteData();
    List<TasteRestDto> getTasteList();

    List<TasteReviewDto> getReviewsByRestaurantId(Long restId, HttpSession session);

    void addReview(TasteReviewDto reviewDto);
    void deleteReview(Long reviewId, Long userId) throws Exception;
}
