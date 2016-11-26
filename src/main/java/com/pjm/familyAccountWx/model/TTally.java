package com.pjm.familyAccountWx.model;

import com.pjm.familyAccountWx.common.IdEntity;

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

    @Column(name = "MONEY", length = 10, scale = 2)
    private BigDecimal money;

    @Column(name = "PAYDATE")
    private Date payDate;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private TAccount tAccount;

    @ManyToOne
    @JoinColumn(name = "PURPOSE_ID")
    private TPurpose tPurpose;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "biz_tally_payUser", joinColumns = {
            @JoinColumn(name = "TALLY_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "PAYUSER_ID")})
    private Set<TPayUser> tPayUserSet = new HashSet<TPayUser>(0);

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
}
