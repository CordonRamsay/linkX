package com.mjc.linkx.commentlike;

import com.mjc.linkx.boardcommon.IBoardBase;

public interface ICommentLike extends IBoardBase {
    Long getId();
    void setId(Long id);

    Long getCommentId();
    void setCommentId(Long commentId);

    default void copyFields(ICommentLike from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getCommentId() != null) {
            this.setCommentId(from.getCommentId());
        }
        if (from.getCreateId() != null) {
            this.setCreateId(from.getCreateId());
        }
    }
}
