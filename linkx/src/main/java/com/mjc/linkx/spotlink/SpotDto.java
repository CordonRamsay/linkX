package com.mjc.linkx.spotlink;

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
public class SpotDto {
    private Long id; // 스팟 id
    private String title; // 스팟 이름
    private String link; // 스팟 사이트
    private String category; // 스팟 카테고리
    private String addr; // 스팟 주소
    private String roadAddr; // 스팟 도로명 주소
    private String mapX; // 스팟 X좌표
    private String mapY; // 스팟 Y좌표
}
