package com.mjc.linkx.petition;


import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

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


}
