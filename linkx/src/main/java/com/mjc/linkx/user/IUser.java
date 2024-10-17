package com.mjc.linkx.user;

public interface IUser {
    Long getId();
    void setId(Long id);

    String getUserId();
    void setUserId(String userId);

    String getUserPassword();
    void setUserPassword(String userPassword);

    String getUserName();
    void setUserName(String userName);

    String getUserNickname();
    void setUserNickname(String userNickname);

    String getUserPhone();
    void setUserPhone(String userPhone);

    String getUserEmail();
    void setUserEmail(String userEmail);

    String getUserUniv();
    void setUserUniv(String userUniv);

    Long getMajorId();
    void setMajorId(Long majorId);

    String getMajorName();
    void setMajorName(String majorName);

    String getUserNum();
    void setUserNum(String userNum);



}
