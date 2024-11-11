package com.mjc.linkx.comment;

import com.mjc.linkx.boardcommon.BoardBaseDto;
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

public class CommentDto extends BoardBaseDto implements IComment{

    private Long id;

    private Long boardId;

    private String comment;

}
