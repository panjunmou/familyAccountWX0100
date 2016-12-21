package com.pjm.familyAccountWx.controller.console;

import com.pjm.familyAccountWx.common.controller.BaseController;
import com.pjm.familyAccountWx.common.enu.BizSeqType;
import com.pjm.familyAccountWx.common.vo.Json;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.service.Accountservice;
import com.pjm.familyAccountWx.vo.AccountVo;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
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
@RequestMapping("/console/account")
public class AccountController extends BaseController {

    @Resource
    private Accountservice accountservice;

    @RequestMapping("/manager")
    public String manager() throws Exception {
        return "/console/account/accountList";
    }

    @RequestMapping("/dataGrid")
    @ResponseBody
    public PageModel dataGrid(AccountVo accountVo, PageModel ph, HttpServletRequest request) {
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        accountVo.setLoginUserInfo(loginUserInfo);
        try {
            PageModel pageModel = accountservice.dataGrid(accountVo, ph);
            return pageModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ph;
    }

    @RequestMapping("/addPage")
    public String addPage(Model model) throws Exception {
        String accountNo = this.getBizSeqCode(BizSeqType.ACCOUNT.getSeqType());
        model.addAttribute("accountNo", accountNo);
        return "/console/account/accountAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Json save(HttpServletRequest request, AccountVo accountVo) {
        Json j = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            accountVo.setCreateUser(loginUserInfo.getName());// �����û�
            accountVo.setLoginUserInfo(loginUserInfo);
            accountservice.save(accountVo);
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
        AccountVo accountVo = accountservice.get(id);
        model.addAttribute("account", accountVo);
        return "/console/account/accountEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public Json update(HttpServletRequest request, AccountVo accountVo) {
        Json j = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            accountVo.setUpdateUser(loginUserInfo.getName());
            accountVo.setUpdateDate(new Date());
            accountVo.setLoginUserInfo(loginUserInfo);
            accountservice.update(accountVo);
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
    public Json delete(AccountVo accountVo) {
        Json j = new Json();
        try {
            accountservice.delete(accountVo.getId());
            j.setMsg("删除成功");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("删除失败");
            e.printStackTrace();
        }
        return j;
    }
}
