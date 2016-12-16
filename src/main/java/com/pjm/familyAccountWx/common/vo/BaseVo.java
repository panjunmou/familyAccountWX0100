package com.pjm.familyAccountWx.common.vo;

import com.pjm.familyAccountWx.common.util.DateUtil;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author AllenPang
 * @date 2016-08-10
 * @desc 基础数据对象基类
 */
@SuppressWarnings("serial")
public class BaseVo implements Serializable {

    protected Long id;

    protected String createUser;

    protected Date createDate;

    protected String updateUser;

    protected Date updateDate;

    protected String createDateStart;

    protected String createDateEnd;

    protected LoginUserInfo loginUserInfo;

    public BaseVo() {
        this.createDate = this.getCreateDate();
        this.createUser = this.getCreateUser();
        this.updateDate = this.getUpdateDate();
        this.updateUser = this.getUpdateUser();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUser() {
        if (loginUserInfo != null && createUser == null) {
            return loginUserInfo.getName();
        }
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        if (createDate == null) {
            createDate = new Date();
        }
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        if (loginUserInfo != null) {
            return loginUserInfo.getName();
        }
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return new Date();
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDateStart() {
        if (!StringUtils.isEmpty(createDateStart)) {
            return DateUtil.stringToDate(createDateStart + " 00:00:00");
        }
        return null;
    }

    public void setCreateDateStart(String createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        if (!StringUtils.isEmpty(createDateEnd)) {
            return DateUtil.stringToDate(createDateEnd + " 23:59:59");
        }
        return null;
    }

    public void setCreateDateEnd(String createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public LoginUserInfo getLoginUserInfo() {
        return loginUserInfo;
    }

    public void setLoginUserInfo(LoginUserInfo loginUserInfo) {
        this.loginUserInfo = loginUserInfo;
    }
}