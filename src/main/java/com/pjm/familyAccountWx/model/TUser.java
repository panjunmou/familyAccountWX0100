package com.pjm.familyAccountWx.model;

import com.pjm.familyAccountWx.common.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by PanJM on 2016/11/19.
 */
@Entity
@Table(name = "biz_user")
public class TUser extends IdEntity {

    @Column(name = "USER_NO", length = 20,nullable = false)
    private String userNo;

    @Column(name = "USERNAME", length = 32)
    private String userName;

    @Column(name = "PASSWORD", length = 64)
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
}
