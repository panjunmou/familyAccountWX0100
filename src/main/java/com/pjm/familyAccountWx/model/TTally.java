package com.pjm.familyAccountWx.model;

import com.pjm.familyAccountWx.common.vo.IdEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PanJM on 2016/11/26.
 */
@Entity
@Table(name = "biz_tally")
public class TTally extends IdEntity {

    @Column(name = "TALLY_NO", length = 32,nullable = false)
    private String tallyNo;

    @Column(name = "MONEY", length = 10, scale = 2)
    private BigDecimal money;

    @Column(name = "PAYDATE")
    private Date payDate;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_NO",referencedColumnName = "ACCOUNT_NO")
    private TAccount tAccount;

    @ManyToOne
    @JoinColumn(name = "PURPOSE_NO",referencedColumnName = "PURPOSE_NO")
    private TPurpose tPurpose;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "biz_tally_payUser", joinColumns = {
            @JoinColumn(name = "TALLY_NO",referencedColumnName = "TALLY_NO")}, inverseJoinColumns = {
            @JoinColumn(name = "PAYUSER_NO",referencedColumnName = "PAYUSER_NO")})
    private Set<TPayUser> tPayUserSet = new HashSet<TPayUser>(0);

    private String remark;

    private boolean visible = true;

    @ManyToOne
    @JoinColumn(name = "USER_NO",referencedColumnName = "USER_NO")
    private TUser tUser;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public TAccount gettAccount() {
        return tAccount;
    }

    public void settAccount(TAccount tAccount) {
        this.tAccount = tAccount;
    }

    public TPurpose gettPurpose() {
        return tPurpose;
    }

    public void settPurpose(TPurpose tPurpose) {
        this.tPurpose = tPurpose;
    }

    public Set<TPayUser> gettPayUserSet() {
        return tPayUserSet;
    }

    public void settPayUserSet(Set<TPayUser> tPayUserSet) {
        this.tPayUserSet = tPayUserSet;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public TUser gettUser() {
        return tUser;
    }

    public void settUser(TUser tUser) {
        this.tUser = tUser;
    }

    public String getTallyNo() {
        return tallyNo;
    }

    public void setTallyNo(String tallyNo) {
        this.tallyNo = tallyNo;
    }
}
