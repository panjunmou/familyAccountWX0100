package com.pjm.familyAccountWx.controller.console;

import com.pjm.familyAccountWx.common.controller.BaseController;
import com.pjm.familyAccountWx.common.enu.BizSeqType;
import com.pjm.familyAccountWx.common.vo.Json;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.service.PayUserService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.PayUserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by PanJM_Levono on 2016/12/14.
 */
@Controller
@RequestMapping("/console/payUser")
public class PayUserController extends BaseController {

    @Resource
    private PayUserService payUserService;

    @RequestMapping("/manager")
    public String manager() throws Exception {
        return "/console/payUser/PayUserList";
    }

    @RequestMapping("/dataGrid")
    @ResponseBody
    public PageModel dataGrid(PayUserVo payUserVo, PageModel ph, HttpServletRequest request) {
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        payUserVo.setLoginUserInfo(loginUserInfo);
        try {
            PageModel pageModel = payUserService.dataGrid(payUserVo, ph);
            return pageModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ph;
    }

    @RequestMapping("/addPage")
    public String addPage(Model model) throws Exception {
        String payUserNo = this.getBizSeqCode(BizSeqType.PAYUSER.getSeqType());
        model.addAttribute("payUserNo", payUserNo);
        return "/console/payUser/payUserAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Json save(HttpServletRequest request, PayUserVo payUserVo) {
        Json j = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            payUserVo.setCreateUser(loginUserInfo.getName());
            payUserVo.setLoginUserInfo(loginUserInfo);
            payUserService.save(payUserVo);
            j.setMsg("保存成功");
            j.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            j.setMsg("保存失败");
        }
        return j;
    }

    @RequestMapping("/editPage")
    public String updatePage(Model model, Long id) throws Exception {
        PayUserVo payUserVo = payUserService.get(id);
        model.addAttribute("payUser", payUserVo);
        return "/console/payUser/payUserEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public Json update(HttpServletRequest request, PayUserVo payUserVo) {
        Json j = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            payUserVo.setUpdateUser(loginUserInfo.getName());
            payUserVo.setUpdateDate(new Date());
            payUserVo.setLoginUserInfo(loginUserInfo);
            payUserService.update(payUserVo);
            j.setMsg("修改成功");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("修改失败");
            e.printStackTrace();
        }
        return j;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(PayUserVo payUserVo) {
        Json j = new Json();
        try {
            payUserService.delete(payUserVo.getId());
            j.setMsg("删除成功");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("删除失败");
            e.printStackTrace();
        }
        return j;
    }
}
