package com.mjc.linkx.user;


import com.mjc.linkx.security.dto.UserRole;

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

    UserRole getRole();
    void setRole(UserRole role);



    default void copyFields(IUser from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getLoginId() != null && !from.getLoginId().isEmpty()) {
            this.setLoginId(from.getLoginId());
        }
        if (from.getPassword() != null && !from.getPassword().isEmpty()) {
            this.setPassword(from.getPassword());
        }
        if (from.getName() != null && !from.getName().isEmpty()) {
            this.setName(from.getName());
        }
        if (from.getNickname() != null && !from.getNickname().isEmpty()) {
            this.setNickname(from.getNickname());
        }
        if (from.getPhone() != null && !from.getPhone().isEmpty()) {
            this.setPhone(from.getPhone());
        }
        if (from.getEmail() != null && !from.getEmail().isEmpty()) {
            this.setEmail(from.getEmail());
        }
        if (from.getUniv() != null && !from.getUniv().isEmpty()) {
            this.setUniv(from.getUniv());
        }
        if (from.getMajorId() != null) {
            this.setMajorId(from.getMajorId());
        }
        if (from.getMajorName() != null) {
            this.setMajorName(from.getMajorName());
        }
        if (from.getStuNum() != null) {
            this.setStuNum(from.getStuNum());
        }
        if (from.getActive() != null) {
            this.setActive(from.getActive());
        }
        if (from.getRole() != null) {
            this.setRole(from.getRole());
        }


    }

}
