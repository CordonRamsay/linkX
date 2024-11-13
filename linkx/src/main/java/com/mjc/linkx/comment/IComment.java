package com.mjc.linkx.comment;

import com.mjc.linkx.boardcommon.IBoardBase;


public interface IComment extends IBoardBase {

    Long getId();
    void setId(Long id);

    Long getBoardId();
    void setBoardId(Long boardId);

    String getComment();
    void setComment(String comment);

    default void copyFields(IComment from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getBoardType() != null && !from.getBoardType().isEmpty()) {
            this.setBoardType(from.getBoardType());
        }
        if (from.getBoardId() != null) {
            this.setBoardId(from.getBoardId());
        }
        if (from.getCreateId() != null) {
            this.setCreateId(from.getCreateId());
        }
        if (from.getCreateDt() != null) {
            this.setCreateDt(from.getCreateDt());
        }
        if (from.getUpdateDt() != null && !from.getUpdateDt().isEmpty()) {
            this.setUpdateDt(from.getUpdateDt());
        }
        if (from.getLikeQty() != null) {
            this.setLikeQty(from.getLikeQty());
        }
        if (from.getDeleteYn() != null) {
            this.setDeleteYn(from.getDeleteYn());
        }
        if (from.getComment() != null) {
            this.setComment(from.getComment());
        }
    }
}
