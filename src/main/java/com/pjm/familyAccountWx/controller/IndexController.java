package com.pjm.familyAccountWx.controller;

import com.pjm.familyAccountWx.service.Accountservice;
import com.pjm.familyAccountWx.service.PayUserService;
import com.pjm.familyAccountWx.service.PurposeService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM on 2016/11/26.
 */
@Controller
@RequestMapping("/index")
public class IndexController {
    @Resource
    private PurposeService purposeService;
    @Resource
    private Accountservice accountservice;
    @Resource
    private PayUserService payUserService;

    /**
     * 列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list() {
        return "tally/list";
    }

    /**
     * 记一笔
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/tally")
    public String tally(Model model, HttpServletRequest request) {
        try {

            LoginUserInfo loginUserInfo = (LoginUserInfo) request.getSession().getAttribute("loginUserInfo");
            String userName = loginUserInfo.getName();
            Long id = loginUserInfo.getId();

            String purposeInList = purposeService.queryPurpose(userName,-1);
            model.addAttribute("purposeInList", purposeInList);

            String purposeOutList = purposeService.queryPurpose(userName,1);
            model.addAttribute("purposeOutList", purposeOutList);

            String accountList = accountservice.queryAccountListByUserId(id);
            model.addAttribute("accountList", accountList);

            String payUserList = payUserService.queryPayUserListByUserId(id);
            model.addAttribute("payUserList", payUserList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "tally/tally";
    }

    /**
     * 报表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/baobiao")
    public String baobiao() {
        return "tally/baobiao";
    }

    /**
     * 保险
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/baoxian")
    public String baoxian() {
        return "tally/baoxian";
    }
}
