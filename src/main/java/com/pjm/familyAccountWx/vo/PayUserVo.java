package com.pjm.familyAccountWx.vo;

import com.pjm.familyAccountWx.common.vo.BaseVo;

/**
 * Created by PanJM_Levono on 2016/12/21.
 */
public class PayUserVo extends BaseVo {
    private String payUserNo;

    private String name;

    private String userNo;

    private String userName;

    private Integer seq;

    public String getPayUserNo() {
        return payUserNo;
    }

    public void setPayUserNo(String payUserNo) {
        this.payUserNo = payUserNo;
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
