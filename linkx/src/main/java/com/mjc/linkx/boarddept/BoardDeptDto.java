package com.mjc.linkx.boarddept;

import com.mjc.linkx.boardcommon.BoardBaseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDeptDto extends BoardBaseDto implements IBoardDept {

    // id
    private Long id;
    //  제목
//    @Size(min = 2, max = 100, message = "제목은 2자~100자 사이로 입력해 주세요.")
    private String title;
    //  내용
//  @Size(min = 2, max = 1000, message = "본문은 2자~1000자 사이로 입력해 주세요.")
    private String content;
    // 학과 코드
    private Long majorId;
    //  댓글 수
    private String countComment;

    public String getBoardType() {
        return "dept";
    }
}
