package com.pjm.familyAccountWx.controller;

import com.pjm.familyAccountWx.common.constants.GlobalConstant;
import com.pjm.familyAccountWx.common.vo.Json;
import com.pjm.familyAccountWx.model.TUser;
import com.pjm.familyAccountWx.service.UserService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM on 2016/11/17.
 */
@Controller
@RequestMapping("/login")
public class LoginCountroller {

    @Resource
    private UserService userService;

    /**
     * 登录页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/loginchoose")
    public String home() {
        return "/login/loginchoose";
    }

    /**
     * 登录页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/loginPage")
    public String loginPage(String type, Model model) {
        model.addAttribute("loginName", type);
        return "/login/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Json login(HttpServletRequest request, String userName, String passWord) throws Exception {
        Json json = new Json();
        TUser tUser = userService.login(userName, passWord);
        if (tUser != null) {
            LoginUserInfo loginUserInfo = new LoginUserInfo();
            loginUserInfo.setId(tUser.getId());
            loginUserInfo.setName(tUser.getUserName());
            request.getSession().setAttribute(GlobalConstant.LOGIN_USER_INFO, loginUserInfo);
            json.setSuccess(true);
            json.setMsg("登录成功");
        } else {
            json.setSuccess(false);
            json.setMsg("登录失败");
        }
        return json;
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public Json logout(HttpServletRequest request) throws Exception {
        Json json = new Json();
        request.getSession().removeAttribute(GlobalConstant.LOGIN_USER_INFO);
        json.setSuccess(true);
        json.setMsg("登录成功");
        return json;
    }
}
