package com.mjc.linkx.boarddept;

import com.mjc.linkx.boardcommon.IBoardBase;

public interface IBoardDept extends IBoardBase {
    Long getId();
    void setId(Long id);

    String getTitle();
    void setTitle(String title);

    String getContent();
    void setContent(String content);

    Integer getViewQty();
    void setViewQty(Integer viewQty);

    Integer getLikeQty();
    void setLikeQty(Integer likeQty);

    Long getMajorId();
    void setMajorId(Long majorId);
}
