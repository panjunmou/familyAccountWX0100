package com.pjm.familyAccountWx.controller.console;

import com.pjm.familyAccountWx.common.controller.BaseController;
import com.pjm.familyAccountWx.common.enu.BizSeqType;
import com.pjm.familyAccountWx.common.vo.Json;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.service.UserService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.UserVo;
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
@RequestMapping("/console/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping("/manager")
    public String manager() throws Exception {
        return "/console/biz/user/userList";
    }

    @RequestMapping("/dataGrid")
    @ResponseBody
    public PageModel dataGrid(UserVo userVo, PageModel ph) {
        try {
            return userService.dataGrid(userVo, ph);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ph;
    }

    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) throws Exception {
        String userNo = this.getBizSeqCode(BizSeqType.USER.getSeqType());
        request.setAttribute("userNo", userNo);
        return "/console/biz/user/userAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Json save(HttpServletRequest request, UserVo userVo) {
        Json j = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            userVo.setCreateUser(loginUserInfo.getName());// �����û�
            userVo.setLoginUserInfo(loginUserInfo);
            userService.save(userVo);
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
        UserVo userVo = userService.get(id);
        model.addAttribute("user", userVo);
        return "/console/biz/user/userEdit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public Json update(HttpServletRequest request, UserVo userVo) {
        Json j = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            userVo.setUpdateUser(loginUserInfo.getName());
            userVo.setUpdateDate(new Date());
            userService.update(userVo);
            j.setMsg("�޸ĳɹ�");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("�޸�ʧ��");
            e.printStackTrace();
        }
        return j;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Json delete(UserVo userVo) {
        Json j = new Json();
        try {
            userService.delete(userVo.getId());
            j.setMsg("ɾ���ɹ�");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("ɾ��ʧ��");
            e.printStackTrace();
        }
        return j;
    }
}
