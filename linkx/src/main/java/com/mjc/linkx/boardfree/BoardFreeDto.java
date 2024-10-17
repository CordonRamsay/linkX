package com.mjc.linkx.boardfree;

import com.mjc.linkx.boardcommon.BoardBaseDto;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BoardFreeDto extends BoardBaseDto implements IBoardFree {

    // id
    private Long id;
    @Size(min = 2, max = 100, message = "제목은 2자~100자 사이로 입력해 주세요.")

    // 제목
    private String title;
    @Size(min = 2, max = 1000, message = "본문은 2자~1000자 사이로 입력해 주세요.")

    // 내용
    private String content;
    // 조회수
    private Integer viewQty;
    // 좋아요 수
    private Integer likeQty;
    // 댓글 수
    private String countComment;

    public String getTbl() {
        return "free";
    }
}
