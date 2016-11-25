package com.pjm.familyAccountWx.controller;

import com.pjm.familyAccountWx.common.Json;
import com.pjm.familyAccountWx.service.UserService;
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
        boolean flag = userService.login(userName, passWord);
        if (flag) {
            request.getSession().setAttribute("userName", userName);
            json.setSuccess(true);
            json.setMsg("登录成功");
        } else {
            json.setSuccess(false);
            json.setMsg("登录失败");
        }
        return json;
    }
}
