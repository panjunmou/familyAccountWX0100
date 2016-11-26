package com.pjm.familyAccountWx.common;

import com.pjm.familyAccountWx.vo.LoginUserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM on 2016/11/26.
 */
public class BaseController {

    protected LoginUserInfo getLoginUserInfo(HttpServletRequest request) {
        LoginUserInfo loginUserInfo = (LoginUserInfo) request.getSession().getAttribute("loginUserInfo");
        return loginUserInfo;
    }
}
