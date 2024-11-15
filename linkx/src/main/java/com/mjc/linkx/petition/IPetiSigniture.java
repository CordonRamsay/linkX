package com.mjc.linkx.petition;

public interface IPetiSigniture extends IPetition {

    Long getId();
    void setId(Long id);

    Long getPetiId();
    void setPetiId(Long petiId);

    String getSigDt();
    void setSigDt(String sigDt);

    default void copyFiedls(IPetiSigniture from) {
        if(from == null){
            return;
        }
        if(from.getId() != null){
            this.setId(from.getId());
        }
        if(from.getPetiId() != null ){
            this.setPetiId(from.getPetiId());
        }
        if(from.getSigDt() != null){
            this.setSigDt(from.getSigDt());
        }
        if(from.getUserId() != null){
            this.setUserId(from.getUserId());
        }

    }

}
