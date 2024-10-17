package com.mjc.linkx.boardlike;

public interface IBoardLike{
    Long getId();
    void setId(Long id);

    String getBoardType();
    void setBoardType(String boardType);

    Long getBoardId();
    void setBoardId(Long boardId);

    Long getCreateId();
    void setCreateId(Long createId);

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
