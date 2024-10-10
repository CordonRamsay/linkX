package com.mjc.linkx.common;


import java.text.SimpleDateFormat;
import java.util.Date;

public interface IBoardBase {
    //작성자 id
    Long getCreateId();
    void setCreateId(Long createId);

    //작성일
    String getCreateDt();
    void setCreateDt(String createDt);

    //작성자 이름
    String getCreateName();
    void setCreateName(String createName);

    //수정일
    String getUpdateDt();
    void setUpdateDt(String updateDt);

    //삭제여부
    Boolean getDeleteYn();
    void setDeleteYn(Boolean deleteYn);

    // 테이블명
    String getTbl();
    void setTbl(String tbl);

    // 현재날짜 출력
    default String getSystemDt() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(today);
    }

    //수정일에 현재날짜 출력
    default void setUpdateInfo(Long memberId) {
        this.setUpdateDt(this.getSystemDt());
    }
}
