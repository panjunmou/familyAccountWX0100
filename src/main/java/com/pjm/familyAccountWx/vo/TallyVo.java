package com.pjm.familyAccountWx.vo;

import java.math.BigDecimal;

/**
 * Created by PanJM on 2016/11/26.
 */
public class TallyVo {

    private Long id;

    private BigDecimal money;

    private String payDate;

    private Long accountId;
    private String accountName;

    private Long purposeId;
    private String purposeName;

    private String payUserIds;
    private String payUserNames;

    private String remark;

    private Integer purposeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Long getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Long purposeId) {
        this.purposeId = purposeId;
    }

    public String getPurposeName() {
        return purposeName;
    }

    public void setPurposeName(String purposeName) {
        this.purposeName = purposeName;
    }

    public String getPayUserIds() {
        return payUserIds;
    }

    public void setPayUserIds(String payUserIds) {
        this.payUserIds = payUserIds;
    }

    public String getPayUserNames() {
        return payUserNames;
    }

    public void setPayUserNames(String payUserNames) {
        this.payUserNames = payUserNames;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(Integer purposeType) {
        this.purposeType = purposeType;
    }
}
