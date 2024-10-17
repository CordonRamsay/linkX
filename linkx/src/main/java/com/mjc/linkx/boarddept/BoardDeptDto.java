package com.mjc.linkx.boarddept;

import com.mjc.linkx.boardcommon.BoardBaseDto;
import jakarta.validation.constraints.Size;

public class BoardDeptDto extends BoardBaseDto implements IBoardDept {

    private Long id;
    @Size(min = 2, max = 100, message = "제목은 2자~100자 사이로 입력해 주세요.")
    private String title;
    @Size(min = 2, max = 1000, message = "본문은 2자~1000자 사이로 입력해 주세요.")
    private String content;
    private Integer viewQty;
    private Integer likeQty;
    private Long majorId;
    private String countComment;
}
