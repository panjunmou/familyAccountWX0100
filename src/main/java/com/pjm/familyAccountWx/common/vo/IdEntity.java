package com.pjm.familyAccountWx.common.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 统一定义id的entity基类.
 * <p>
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class IdEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", length = 10)
    protected Long id;

    @Column(name = "CREATE_USER", length = 20)
    protected String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", length = 19)
    protected Date createDate;

    @Column(name = "UPDATE_USER", length = 20)
    protected String updateUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE", length = 19)
    protected Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
