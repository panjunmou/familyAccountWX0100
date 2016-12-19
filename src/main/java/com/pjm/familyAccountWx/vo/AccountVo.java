package com.pjm.familyAccountWx.vo;

import com.pjm.familyAccountWx.common.vo.BaseVo;

/**
 * Created by PanJM_Levono on 2016/12/19.
 */
public class AccountVo extends BaseVo {
    private String accountNo;

    private String name;

    private String userNo;

    private String userName;

    private Integer seq;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
