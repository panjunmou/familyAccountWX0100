package com.pjm.familyAccountWx.common.enu;

public enum BizSeqType {

    TALLY("tally", "账目明细"),
    ACCOUNT("account", "账户"),
    PURPOSE("purpose", "用途"),
    PAYUSER("payuser", "使用者"),
    USER("user", "用户");

    private String seqType;
    private String description;

    private BizSeqType(String seqType, String description) {
        this.seqType = seqType;
        this.description = description;
    }

    public String getSeqType() {
        return seqType;
    }

    public void setSeqType(String seqType) {
        this.seqType = seqType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
