package com.mjc.linkx.petition;

public interface IPetition {

    //Dto에 정의한 필드 모두 get/set 메소드 만들기

    Long getId();
    void setId(Long id);

    String getPetiTitle();
    void setPetiTitle(String petiTitle);

    String getPetiField();
    void setPetiField(String petiField);

/*    String getPetiEffect();                   //사용 중지(
    void setPetiEffect(String petiEffect);*/

    String getPetiContent();
    void setPetiContent(String petiContent);

    Long getUserId();
    void setUserId(Long userId);

    String getCreateDt();
    void setCreateDt(String createDt);

    String getEndDt();
    void setEndDt(String endDt);

    Integer getAgreeQty();
    void setAgreeQty(Integer agreeQty);

    Boolean getDeleteYn();
    void setDeleteYn(Boolean deleteYn);

    String getUserNickName();
    void setUserNickName(String userNickName);

    Boolean getPlaying();
    void setPlaying(Boolean playing);

}
