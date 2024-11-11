package com.mjc.linkx.boardlike;

import com.mjc.linkx.boardcommon.IBoardBase;

public interface IBoardLike extends IBoardBase {
    Long getId();
    void setId(Long id);

    Long getBoardId();
    void setBoardId(Long boardId);


    default void copyFields(IBoardLike from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getBoardType() != null && !from.getBoardType().isEmpty()) {
            this.setBoardType(from.getBoardType());
        }
        if (from.getCreateId() != null) {
            this.setCreateId(from.getCreateId());
        }
        if (from.getBoardId() != null) {
            this.setBoardId(from.getBoardId());
        }
    }
}
