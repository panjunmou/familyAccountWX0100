package com.pjm.familyAccountWx.model;

import com.pjm.familyAccountWx.common.IdEntity;

import javax.persistence.*;

/**
 * Created by PanJM on 2016/11/26.
 */
@Entity
@Table(name = "biz_account")
public class TAccount extends IdEntity {

    @Column(name = "ACCOUNT_NO",length = 32,nullable = false)
    private String accountNo;

    @Column(name = "NAME", length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_NO",referencedColumnName = "USER_NO")
    private TUser tUser;

    @Column(name = "SEQ", length = 2)
    private Integer seq;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TUser gettUser() {
        return tUser;
    }

    public void settUser(TUser tUser) {
        this.tUser = tUser;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}
