package com.pjm.familyAccountWx.vo;

import com.pjm.familyAccountWx.common.vo.BaseVo;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by PanJM_Levono on 2016/12/21.
 */
public class PurposeVo extends BaseVo {
    private String purposeNo;

    private String name;

    private String pId;

    private String userNo;

    private String userName;

    private boolean visible;

    private Integer purposeType;

    private Boolean hasParent;

    public String getPurposeNo() {
        return purposeNo;
    }

    public void setPurposeNo(String purposeNo) {
        this.purposeNo = purposeNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Integer getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(Integer purposeType) {
        this.purposeType = purposeType;
    }

    public Boolean getHasParent() {
        return hasParent;
    }

    public void setHasParent(Boolean hasParent) {
        this.hasParent = hasParent;
    }
}
