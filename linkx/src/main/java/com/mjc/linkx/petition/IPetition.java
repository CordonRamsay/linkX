package com.mjc.linkx.petition;

public interface IPetition {

    //Dto에 정의한 필드 모두 get/set 메소드 만들기

    Long getId();
    void setId(Long id);

    String getPetiTitle();
    void setPetiTitle(String petiTitle);

    String getPetiField();
    void setPetiField(String petiField);

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

    Boolean getisSig();
    void setisSig(Boolean isSig);

    default void copyFields(IPetition from){
        if(from == null){
            return;
        }
        if(from.getId() != null){
            this.setId(from.getId());
        }
        if(from.getPetiTitle() != null && !from.getPetiTitle().isEmpty()){
            this.setPetiTitle(from.getPetiTitle());
        }
        if(from.getPetiField() != null && !from.getPetiField().isEmpty()){
            this.setPetiField(from.getPetiField());
        }
        if(from.getPetiContent() != null && !from.getPetiContent().isEmpty()){
            this.setPetiContent(from.getPetiContent());
        }
        if(from.getUserId() != null){
            this.setUserId(from.getUserId());
        }
        if(from.getCreateDt() != null){
            this.setCreateDt(from.getCreateDt());
        }
        if(from.getEndDt() != null){
            this.setEndDt(from.getEndDt());
        }
        if(from.getAgreeQty() != null){
            this.setAgreeQty(from.getAgreeQty());
        }
        if(from.getDeleteYn() != null){
            this.setDeleteYn(from.getDeleteYn());
        }
        if(from.getUserNickName() != null && !from.getUserNickName().isEmpty()){
            this.setUserNickName(from.getUserNickName());
        }
        if(from.getPlaying() != null){
            this.setPlaying(from.getPlaying());
        }
    }


}
