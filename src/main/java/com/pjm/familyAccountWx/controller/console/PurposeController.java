package com.pjm.familyAccountWx.controller.console;

import com.pjm.familyAccountWx.common.controller.BaseController;
import com.pjm.familyAccountWx.common.enu.BizSeqType;
import com.pjm.familyAccountWx.common.vo.Json;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.service.PurposeService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.PurposeVo;
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
@RequestMapping("/console/purpose")
public class PurposeController extends BaseController {

    @Resource
    private PurposeService purposeService;

    @RequestMapping("/manager")
    public String manager(HttpServletRequest request) throws Exception {
        String pId = request.getParameter("pId");
        request.setAttribute("pId", pId);
        return "/console/purpose/purposeList";
    }

    @RequestMapping("/dataGrid")
    @ResponseBody
    public PageModel dataGrid(PurposeVo purposeVo, PageModel ph, HttpServletRequest request,String pId) {
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        purposeVo.setLoginUserInfo(loginUserInfo);
        try {
            PageModel pageModel = purposeService.dataGrid(purposeVo, ph,pId);
            return pageModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ph;
    }

    @RequestMapping("/addPage")
    public String addPage(Model model, HttpServletRequest request) throws Exception {
        String purposeNo = this.getBizSeqCode(BizSeqType.PURPOSE.getSeqType());
        model.addAttribute("purposeNo", purposeNo);
        String pId = request.getParameter("pId");
        model.addAttribute("pId", pId);
        return "/console/purpose/purposeAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Json save(HttpServletRequest request, PurposeVo purposeVo) {
        Json j = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            purposeVo.setCreateUser(loginUserInfo.getName());
            purposeVo.setLoginUserInfo(loginUserInfo);
            purposeService.save(purposeVo);
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
        PurposeVo purposeVo = purposeService.get(id);
        model.addAttribute("purpose", purposeVo);
        return "/console/purpose/purposeEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public Json update(HttpServletRequest request, PurposeVo purposeVo) {
        Json j = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            purposeVo.setUpdateUser(loginUserInfo.getName());
            purposeVo.setUpdateDate(new Date());
            purposeVo.setLoginUserInfo(loginUserInfo);
            purposeService.update(purposeVo);
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
    public Json delete(PurposeVo purposeVo) {
        Json j = new Json();
        try {
            purposeService.delete(purposeVo.getId());
            j.setMsg("删除成功");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("删除失败");
            e.printStackTrace();
        }
        return j;
    }

    @RequestMapping("/changeStatus")
    @ResponseBody
    public Json changeStatus(Long id,String status) {
        Json j = new Json();
        try {
            purposeService.changeStatus(id,status);
            j.setMsg("变更成功");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("变更失败");
            e.printStackTrace();
        }
        return j;
    }
}
