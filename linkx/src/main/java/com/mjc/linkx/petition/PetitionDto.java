package com.mjc.linkx.petition;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PetitionDto implements IPetition{

    // DB테이블 참고해서 필드 모두 정의하기(private)
    private Long id;                    //청원 게시글 id
    @Size(min = 5, max = 30)            //청원 게시글 제목
    private String petiTitle;
    private String petiField;            //청원 분야
/*    @Size(min = 5, max = 50)            //청원 취지(캔슬,사용 안함)
    private String petiEffect;*/
    @Size(min = 100, max = 1000)        //청원글 내용
    private String petiContent;
    private Long userId;                //유저아이디(외래키,유저테이블)
    private String createDt;            //청원 작성일
    private String endDt;               //청원 마감일
    private Integer agreeQty;           //동의자 수
    private Boolean deleteYn;           //화면에 보여주는 여부
    private String userNickName;        //사용자 닉네임
    private Boolean playing;            //청원이 현재 진행중인지 종료됬는지 확인하는 필드, 진행중일 경우 1, 종료됬으면 0으로 값을 업데이트 한다.
    private Boolean isSig;
    public String getBoardType() {
        return "petition";
    }       //현재 게시판이 어떤 게시판인지 알려주기 위한 코드, 일단 코드를 따라 적었으며 return을 통해 청원 게시판임을 알림

    //D-day 계산 메서드 추가
    public long getDaysLeft(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate endDate; // 종료일에 시간이 포함되어 있는지 확인하고 적절한 형식으로 변환
         if (this.endDt.length() == 10) {
             endDate = LocalDate.parse(this.endDt, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
         } else {
             endDate = LocalDate.parse(this.endDt, formatter);
         }
         LocalDate today = LocalDate.now();
         return ChronoUnit.DAYS.between(today, endDate);
    }
    //마스킹 된 이름 반환
    public String getMaskedUserNickName() {
        return maskName(userNickName);
    }
    //이름 마스킹
    private String maskName(String name) {
        if(name == null || name.length() < 2){
            return name;
        }
        if(name.length() == 2){
            return name.charAt(0) + "*";
        }
        else{
            StringBuilder maskedName = new StringBuilder();
            maskedName.append(name.charAt(0));
            for(int i = 1; i < name.length()-1; i++){
                maskedName.append("*");
            }
            maskedName.append(name.charAt(name.length()-1));
            return maskedName.toString();
        }
    }

    //남은 시간 계산 메서드 추가
    public Map<String, Object> getRemainingTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime endDate;
        if(this.endDt.length() == 10) {
            endDate = LocalDateTime.parse(this.endDt+" 00:00:00", formatter);
        }else{
            endDate = LocalDateTime.parse(this.endDt, formatter);
        }
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now,endDate);
        long daysLeft = duration.toDays();
        long hoursLeft = duration.toHours();
        long minutesLeft = duration.toMinutes();
        long secondsLeft = duration.getSeconds();

        Map<String, Object> remainingTime = new HashMap<>();
        if(daysLeft == 0){
            remainingTime.put("daysLeft", daysLeft);
            remainingTime.put("hoursLeft", hoursLeft);
            remainingTime.put("minutesLeft", minutesLeft);
            remainingTime.put("secondsLeft", secondsLeft);
        }else{
            remainingTime.put("daysLeft", "D-" + daysLeft);
        }
        return remainingTime;
    }



    @Override
    public Boolean getisSig() {
        return this.isSig;
    }

    @Override
    public void setisSig(Boolean isSig) {
        this.isSig = isSig;
    }
}
