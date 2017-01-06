package com.pjm.familyAccountWx.vo;

import com.pjm.familyAccountWx.common.vo.BaseVo;

import java.math.BigDecimal;

/**
 * Created by PanJM on 2016/11/26.
 */
public class TallyVo extends BaseVo{

    private Long id;

    private String tabId;

    private BigDecimal money;

    private String payDate;

    private Long accountId;
    private String accountName;

    private Long purposeId;
    private Long parentPurposeId;
    private String purposeName;

    private String payUserId;
    private String payUserIds;
    private String payUserNames;

    private String remark;

    private Integer purposeType;

    private String tallyNo;

    private Boolean visible = true;

    private Boolean status;

    private BigDecimal moneyFrom;

    private BigDecimal moneyTo;

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

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public String getPayUserId() {
        return payUserId;
    }

    public void setPayUserId(String payUserId) {
        this.payUserId = payUserId;
    }

    public String getTallyNo() {
        return tallyNo;
    }

    public void setTallyNo(String tallyNo) {
        this.tallyNo = tallyNo;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Long getParentPurposeId() {
        return parentPurposeId;
    }

    public void setParentPurposeId(Long parentPurposeId) {
        this.parentPurposeId = parentPurposeId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BigDecimal getMoneyFrom() {
        return moneyFrom;
    }

    public void setMoneyFrom(BigDecimal moneyFrom) {
        this.moneyFrom = moneyFrom;
    }

    public BigDecimal getMoneyTo() {
        return moneyTo;
    }

    public void setMoneyTo(BigDecimal moneyTo) {
        this.moneyTo = moneyTo;
    }
}
