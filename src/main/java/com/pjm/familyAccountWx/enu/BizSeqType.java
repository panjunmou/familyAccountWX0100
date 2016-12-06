package com.pjm.familyAccountWx.enu;

public enum BizSeqType {

    ACCOUNT("ACCOUNT", "账户"),
    PAYUSER("PAYUSER", "使用者"),
    PURPOSE("PURPOSE", "用途"),
    TALLY("TALLY", "开支"),
    USER("USER", "用户");

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
