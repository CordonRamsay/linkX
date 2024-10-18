package com.mjc.linkx.comment;


import com.mjc.linkx.boardcommon.SearchBoardDto;
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
public class SearchCommentDto extends SearchBoardDto {
    private String boardId;
    private Long createId;
    private String tbl;
    private Long commentId;
}