package com.pjm.familyAccountWx.controller;

import com.pjm.familyAccountWx.service.TallyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM on 2016/11/22.
 */
@Controller
@RequestMapping("/tally")
public class TallyController {

    @Resource
    private TallyService tallyService;

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
            String userName = (String) request.getSession().getAttribute("userName");
            String purposeList = tallyService.queryPurpose(userName);
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
