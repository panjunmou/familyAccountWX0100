package com.pjm.familyAccountWx.controller;

import com.pjm.familyAccountWx.common.BaseController;
import com.pjm.familyAccountWx.common.Json;
import com.pjm.familyAccountWx.service.TallyService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.TallyParam;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM on 2016/11/22.
 */
@Controller
@RequestMapping("/tally")
public class TallyController extends BaseController {

    @Resource
    private TallyService tallyService;

    @RequestMapping(value = "saveTally", method = RequestMethod.POST)
    @ResponseBody
    public Json saveTally(TallyParam tallyParam, HttpServletRequest request) throws Exception {
        Json json = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            tallyService.addTally(tallyParam, loginUserInfo.getName());
            json.setSuccess(true);
            json.setMsg("保存成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
