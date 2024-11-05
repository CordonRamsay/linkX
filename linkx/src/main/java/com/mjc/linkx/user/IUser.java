package com.mjc.linkx.user;

public interface IUser {
    Long getId();
    void setId(Long id);

    String getLoginId();
    void setLoginId(String LoginId);

    String getPassword();
    void setPassword(String password);

    String getName();
    void setName(String Name);

    String getNickname();
    void setNickname(String Nickname);

    String getPhone();
    void setPhone(String Phone);

    String getEmail();
    void setEmail(String Email);

    String getUniv();
    void setUniv(String Univ);

    Long getMajorId();
    void setMajorId(Long majorId);

    String getMajorName();
    void setMajorName(String majorName);

    String getStuNum();
    void setStuNum(String stuNum);

    Boolean getActive();
    void setActive(Boolean active);



}
