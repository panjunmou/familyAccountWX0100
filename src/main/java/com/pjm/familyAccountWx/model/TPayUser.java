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

    @Column(name = "NAME",length = 20)
    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private TUser tUser;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "biz_tally_payUser", joinColumns = {
            @JoinColumn(name = "PAYUSER_ID")}, inverseJoinColumns = {
            @JoinColumn(name = "TALLY_ID")})
    private Set<TTally> tTallySet = new HashSet<TTally>(0);

    @Column(name = "SEQ",length = 2)
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
}
