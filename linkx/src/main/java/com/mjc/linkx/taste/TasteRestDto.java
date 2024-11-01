package com.mjc.linkx.taste;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TasteRestDto {
    private Long id; // 식당 id
    private String title; // 식당 이름
    private String link; // 식당 사이트
    private String category; // 식당 카테고리
    private String addr; // 식당 주소
    private String roadAddr; // 식당 도로명 주소
    private String mapX; // 식당 X좌표
    private String mapY; // 식당 Y좌표
}
