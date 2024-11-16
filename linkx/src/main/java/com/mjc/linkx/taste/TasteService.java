// TasteService.java
package com.mjc.linkx.taste;

import java.util.List;

public interface TasteService {
    String fetchAndSaveTasteData();
    List<TasteRestDto> getTasteList();
    List<TasteReviewDto> getReviewsByRestaurantId(Long restId);
    void addReview(TasteReviewDto reviewDto);
}
