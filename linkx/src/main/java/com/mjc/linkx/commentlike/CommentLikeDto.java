package com.mjc.linkx.commentlike;

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

public class CommentLikeDto extends BoardBaseDto implements ICommentLike{

    private Long id;
    private Long commentId;

}
