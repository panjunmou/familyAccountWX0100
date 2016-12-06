package com.pjm.familyAccountWx.model;

import com.pjm.familyAccountWx.common.IdEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PanJM on 2016/11/26.
 */
@Entity
@Table(name = "biz_payUser")
public class TPayUser extends IdEntity {

    @Column(name = "PAYUSER_NO", length = 20,nullable = false)
    private String payUserNo;

    @Column(name = "NAME", length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_NO",referencedColumnName = "USER_NO")
    private TUser tUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "biz_tally_payUser", joinColumns = {
            @JoinColumn(name = "PAYUSER_NO",referencedColumnName = "PAYUSER_NO")}, inverseJoinColumns = {
            @JoinColumn(name = "TALLY_NO",referencedColumnName = "TALLY_NO")})
    private Set<TTally> tTallySet = new HashSet<TTally>(0);

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

    public Set<TTally> gettTallySet() {
        return tTallySet;
    }

    public void settTallySet(Set<TTally> tTallySet) {
        this.tTallySet = tTallySet;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getPayUserNo() {
        return payUserNo;
    }

    public void setPayUserNo(String payUserNo) {
        this.payUserNo = payUserNo;
    }
}
