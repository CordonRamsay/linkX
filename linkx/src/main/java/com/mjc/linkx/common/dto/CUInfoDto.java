package com.mjc.linkx.common.dto;


import com.mjc.linkx.boardcommon.IBoardBase;
import com.mjc.linkx.user.IUser;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class CUInfoDto {
    private final IUser loginUser;

    public CUInfoDto(IUser loginUser) {
        this.loginUser = loginUser;
    }

    // 현재 날짜,시간 출력 메소드
    private String getSystemDt() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(today);
    }

    // IBoardBase 타입의 객체에 createDt(작성일), createId(작성자의 id : 로그인한 유저의 id ) 값을 세팅
    public void setCreateInfo(IBoardBase iBase) {
        if (iBase == null) {
            return;
        }
        iBase.setCreateDt(this.getSystemDt());
        iBase.setCreateId(loginUser.getId());
    }

    // IBoardBase 타입의 객체에 updateDt(수정일) 값을 세팅
    public void setUpdateInfo(IBoardBase iBase) {
        if (iBase == null) {
            return;
        }
        iBase.setUpdateDt(this.getSystemDt());
    }


}
