package com.pjm.familyAccountWx.common.controller;

import com.pjm.familyAccountWx.common.constants.GlobalConstant;
import com.pjm.familyAccountWx.common.service.BaseService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM on 2016/11/26.
 */
public class BaseController {

    @Resource
    private BaseService baseService;

    protected LoginUserInfo getLoginUserInfo(HttpServletRequest request) {
        LoginUserInfo loginUserInfo = (LoginUserInfo) request.getSession().getAttribute(GlobalConstant.LOGIN_USER_INFO);
        return loginUserInfo;
    }

    /**
     * 根据业务类型获取业务流水编号
     *
     * @param seqType
     *            业务类型
     * @return 业务编号
     */
    protected String getBizSeqCode(String seqType) throws Exception {
        return baseService.callBizSeqCode(seqType);
    }
}
