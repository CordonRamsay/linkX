package com.mjc.linkx.boarddept;

import com.mjc.linkx.boardcommon.IBoardBase;
import com.mjc.linkx.boardfree.IBoardFree;

public interface IBoardDept extends IBoardBase {
    Long getId();
    void setId(Long id);

    String getTitle();
    void setTitle(String title);

    String getContent();
    void setContent(String content);


    Long getMajorId();
    void setMajorId(Long majorId);

    String getCountComment();
    void setCountComment(String countComment);


    default void copyFields(IBoardDept from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getTitle() != null && !from.getTitle().isEmpty()) {
            this.setTitle(from.getTitle());
        }
        if (from.getContent() != null && !from.getContent().isEmpty()) {
            this.setContent(from.getContent());
        }
        if (from.getViewQty() != null) {
            this.setViewQty(from.getViewQty());
        }
        if (from.getLikeQty() != null) {
            this.setLikeQty(from.getLikeQty());
        }
        if (from.getCreateDt() != null && !from.getCreateDt().isEmpty()) {
            this.setCreateDt(from.getCreateDt());
        }
        if (from.getCreateId() != null) {
            this.setCreateId(from.getCreateId());
        }
        if (from.getUpdateDt() != null && !from.getUpdateDt().isEmpty()) {
            this.setUpdateDt(from.getUpdateDt());
        }
        if (from.getDeleteYn() != null) {
            this.setDeleteYn(from.getDeleteYn());
        }
        if (from.getCreateName() != null) {
            this.setCreateName(from.getCreateName());
        }
        if (from.getCountComment() != null) {
            this.setCountComment(from.getCountComment());
        }
        if (from.getLikeYn() != null) {
            this.setLikeYn(from.getLikeYn());
        }

    }
}
