package com.pjm.familyAccountWx.common.controller;

import com.pjm.familyAccountWx.common.constants.GlobalConstant;
import com.pjm.familyAccountWx.vo.LoginUserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM on 2016/11/26.
 */
public class BaseController {

    protected LoginUserInfo getLoginUserInfo(HttpServletRequest request) {
        LoginUserInfo loginUserInfo = (LoginUserInfo) request.getSession().getAttribute(GlobalConstant.LOGIN_USER_INFO);
        return loginUserInfo;
    }
}
