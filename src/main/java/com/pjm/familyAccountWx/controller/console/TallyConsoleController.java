package com.pjm.familyAccountWx.controller.console;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pjm.familyAccountWx.common.controller.BaseController;
import com.pjm.familyAccountWx.common.enu.BizSeqType;
import com.pjm.familyAccountWx.common.vo.Json;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.service.Accountservice;
import com.pjm.familyAccountWx.service.PayUserService;
import com.pjm.familyAccountWx.service.PurposeService;
import com.pjm.familyAccountWx.service.TallyService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.PurposePicker;
import com.pjm.familyAccountWx.vo.SelectVo;
import com.pjm.familyAccountWx.vo.TallyVo;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PanJM_Levono on 2016/12/14.
 */
@Controller
@RequestMapping("/console/tally")
public class TallyConsoleController extends BaseController {

    @Resource
    private TallyService tallyService;
    @Resource
    private Accountservice accountservice;
    @Resource
    private PayUserService payUserService;
    @Resource
    private PurposeService purposeService;

    @RequestMapping("/manager")
    public String manager() throws Exception {
        return "/console/tally/tallyList";
    }

    @RequestMapping("/dataGrid")
    @ResponseBody
    public PageModel dataGrid(TallyVo tallyVo, PageModel ph, HttpServletRequest request) {
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        try {
            Long userId = loginUserInfo.getId();
            tallyVo.setVisible(false);
            PageModel pageModel = tallyService.getTallyList(tallyVo, ph, userId);
            return pageModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ph;
    }

    @RequestMapping("/addPage")
    public String addPage(Model model, HttpServletRequest request) throws Exception {
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        Long userId = loginUserInfo.getId();
        String userName = loginUserInfo.getName();
        String tallyNo = this.getBizSeqCode(BizSeqType.TALLY.getSeqType());
        model.addAttribute("tallyNo", tallyNo);
        Date todayDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(todayDate);
        model.addAttribute("today", today);
        String accountStr = accountservice.queryAccountListByUserId(userId);
        List<SelectVo> accountList = JSON.parseObject(accountStr, new TypeReference<ArrayList<SelectVo>>() {
        });
        model.addAttribute("accountList", accountList);
        String payUserStr = payUserService.queryPayUserListByUserId(userId);
        List<SelectVo> payUserList = JSON.parseObject(payUserStr, new TypeReference<ArrayList<SelectVo>>() {
        });
        model.addAttribute("payUserList", payUserList);
        return "/console/tally/tallyAdd";
    }

    @RequestMapping("/editPage")
    public String updatePage(Model model, Long id) throws Exception {
        TallyVo tallyVo = tallyService.getTally(id);
        model.addAttribute("tally", tallyVo);
        return "/console/tally/tallyEdit";
    }

    @RequestMapping("/changeStatus")
    @ResponseBody
    public Json changeStatus(Long id, String status) {
        Json j = new Json();
        try {
            tallyService.changeStatus(id, status);
            j.setMsg("变更成功");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("变更失败");
            e.printStackTrace();
        }
        return j;
    }

    @RequestMapping(value = "purposeInList", method = RequestMethod.GET)
    @ResponseBody
    public Json purposeInList(HttpServletRequest request) throws Exception {
        Json json = new Json();
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        try {
            String purposeInStr = purposeService.queryPurpose(loginUserInfo.getName(), -1);
            List<PurposePicker> purposeInList = JSON.parseObject(purposeInStr, new TypeReference<ArrayList<PurposePicker>>() { });
            json.setMsg("操作成功");
            json.setObj(purposeInList);
            json.setSuccess(true);
        } catch (ServiceException e) {
            e.printStackTrace();
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(value = "purposeOutList", method = RequestMethod.GET)
    @ResponseBody
    public Json purposeOutList(HttpServletRequest request) throws Exception {
        Json json = new Json();
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        try {

            String purposeOutStr = purposeService.queryPurpose(loginUserInfo.getName(), 1);
            List<PurposePicker> purposeOutList = JSON.parseObject(purposeOutStr, new TypeReference<ArrayList<PurposePicker>>() { });
            json.setObj(purposeOutList);
            json.setSuccess(true);
        } catch (ServiceException e) {
            e.printStackTrace();
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
