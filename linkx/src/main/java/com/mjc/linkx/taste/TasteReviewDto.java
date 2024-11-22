package com.mjc.linkx.taste;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TasteReviewDto {
    private Long id;              // 리뷰 ID
    private Long restId;          // 음식점 ID (리뷰가 연결된 음식점)
    private Long userId;          // 작성자 사용자 ID
    private String reviewTitle;   // 리뷰 제목
    private String reviewContent; // 리뷰 내용
    private int reviewStar;       // 리뷰 별점
    private int reviewLike;       // 리뷰 좋아요 수
    private String userNickName;  // 작성자 닉네임
    private LocalDateTime reviewDate; // 리뷰 작성 날짜
}
