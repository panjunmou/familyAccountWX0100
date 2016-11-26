package com.pjm.familyAccountWx.vo;

import java.math.BigDecimal;

/**
 * Created by PanJM on 2016/11/26.
 */
public class TallyParam {

    private String tabId;

    private BigDecimal money;

    private String payDate;

    private Long accountId;

    private Long purposeId;

    private String payUserIds;

    private String remark;

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
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

    public Long getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Long purposeId) {
        this.purposeId = purposeId;
    }

    public String getPayUserIds() {
        return payUserIds;
    }

    public void setPayUserIds(String payUserIds) {
        this.payUserIds = payUserIds;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
