package com.pjm.familyAccountWx.controller;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.common.controller.BaseController;
import com.pjm.familyAccountWx.common.vo.Json;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.service.Accountservice;
import com.pjm.familyAccountWx.service.PayUserService;
import com.pjm.familyAccountWx.service.PurposeService;
import com.pjm.familyAccountWx.service.TallyService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.TallyVo;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Resource
    private PurposeService purposeService;
    @Resource
    private Accountservice accountservice;
    @Resource
    private PayUserService payUserService;

    @RequestMapping(value = "saveTally", method = RequestMethod.POST)
    @ResponseBody
    public Json saveTally(TallyVo tallyParam, HttpServletRequest request) throws Exception {
        Json json = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            tallyService.addTally(tallyParam, loginUserInfo.getName(),loginUserInfo.getId());
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

    @RequestMapping(value = "updateTally", method = RequestMethod.POST)
    @ResponseBody
    public Json updateTally(TallyVo tallyParam, HttpServletRequest request) throws Exception {
        Json json = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            tallyService.updateTally(tallyParam, loginUserInfo.getName());
            json.setSuccess(true);
            json.setMsg("修改成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(value = "tallyList", method = RequestMethod.GET)
    @ResponseBody
    public Json tallyList(TallyVo tallyParam, PageModel ph,HttpServletRequest request) throws Exception {
        Json json = new Json();
        json.setSuccess(true);
        json.setMsg(null);
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        Long userId = loginUserInfo.getId();
        PageModel pageModel = tallyService.getTallyList(tallyParam, ph,userId);
        json.setObj(pageModel);
        return json;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Json delete(Long id) throws Exception {
        Json json = new Json();
        try {
            tallyService.delete(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (ServiceException e) {
            e.printStackTrace();
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(value = "viewPage")
    public String viewPage(Long id, Model model) throws Exception {
        TallyVo tallyVo = tallyService.getTally(id);
        model.addAttribute("tally", tallyVo);
        return "/tally/tallyView";
    }

    @RequestMapping(value = "editPage")
    public String editPage(Long id, Model model, HttpServletRequest request) throws Exception {
        TallyVo tallyVo = tallyService.getTally(id);
        model.addAttribute("tally", tallyVo);
        Integer purposeType = tallyVo.getPurposeType();
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        String userName = loginUserInfo.getName();
        Long loginUserInfoId = loginUserInfo.getId();
        String accountList = accountservice.queryAccountListByUserId(loginUserInfoId);
        model.addAttribute("accountList", accountList);

        String payUserList = payUserService.queryPayUserListByUserId(loginUserInfoId);
        model.addAttribute("payUserList", payUserList);

        if (purposeType == -1) {
            //支出
            String purposeInList = purposeService.queryPurpose(userName, -1);
            model.addAttribute("purposeInList", purposeInList);
            return "tally/tallyInEdit";
        } else {
            String purposeOutList = purposeService.queryPurpose(userName, 1);
            model.addAttribute("purposeOutList", purposeOutList);
            return "tally/tallyInEdit";
        }
    }
}
