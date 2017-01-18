package com.pjm.familyAccountWx.vo;

import java.math.BigDecimal;

/**
 * Created by PanJM_Levono on 2017/1/17.
 */
public class ReportTableVo implements Comparable{

    private String purposeNo;

    private String purposeName;

    private BigDecimal[] money;

    private Integer seq;

    public String getPurposeNo() {
        return purposeNo;
    }

    public void setPurposeNo(String purposeNo) {
        this.purposeNo = purposeNo;
    }

    public String getPurposeName() {
        return purposeName;
    }

    public void setPurposeName(String purposeName) {
        this.purposeName = purposeName;
    }

    public BigDecimal[] getMoney() {
        return money;
    }

    public void setMoney(BigDecimal[] money) {
        this.money = money;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public int compareTo(Object o) {
        ReportTableVo reportTableVo = (ReportTableVo) o;
        Integer seq = reportTableVo.getSeq();
        return this.seq.compareTo(seq);
    }
}
