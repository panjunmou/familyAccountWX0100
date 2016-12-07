package com.pjm.familyAccountWx.model;

import com.pjm.familyAccountWx.common.vo.IdEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PanJM on 2016/11/22.
 */
@Entity
@Table(name = "biz_purpose")
public class TPurpose extends IdEntity {

    @Column(name = "PURPOSE_NO", length = 32, nullable = false)
    private String purposeNo;

    @Column(name = "NAME", length = 20)
    private String name;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<TPurpose> children = new HashSet<TPurpose>(0);

    @ManyToOne
    @JoinColumn(name = "PARENT_NO", referencedColumnName = "PURPOSE_NO")
    private TPurpose parent;

    @ManyToOne
    @JoinColumn(name = "USER_NO", referencedColumnName = "USER_NO")
    private TUser tUser;

    @Column(name = "VISIBLE")
    private boolean visible;

    @Column(name = "PURPOSE_TYPE", length = 2)
    private Integer purposeType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TPurpose> getChildren() {
        return children;
    }

    public void setChildren(Set<TPurpose> children) {
        this.children = children;
    }

    public TPurpose getParent() {
        return parent;
    }

    public void setParent(TPurpose parent) {
        this.parent = parent;
    }

    public TUser gettUser() {
        return tUser;
    }

    public void settUser(TUser tUser) {
        this.tUser = tUser;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Integer getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(Integer purposeType) {
        this.purposeType = purposeType;
    }

    public String getPurposeNo() {
        return purposeNo;
    }

    public void setPurposeNo(String purposeNo) {
        this.purposeNo = purposeNo;
    }
}
