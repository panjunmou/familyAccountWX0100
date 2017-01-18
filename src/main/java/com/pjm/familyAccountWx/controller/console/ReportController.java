package com.pjm.familyAccountWx.controller.console;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.common.controller.BaseController;
import com.pjm.familyAccountWx.common.util.DateUtil;
import com.pjm.familyAccountWx.common.vo.Json;
import com.pjm.familyAccountWx.service.ReportService;
import com.pjm.familyAccountWx.vo.BarVo;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.ReportTableVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by PanJM_Levono on 2016/12/28.
 */
@Controller
@RequestMapping("/console/report")
public class ReportController extends BaseController {

    @Resource
    private ReportService reportService;

    @RequestMapping("/monthManager")
    public String monthManager(HttpServletRequest request, Model model){
        try {
            String monthFirstStr = DateUtil.getMonthFirstStr(new Date());
            String monthLastStr = DateUtil.getMonthLastStr(new Date());
            model.addAttribute("dateStart", monthFirstStr);
            model.addAttribute("dateEnd", monthLastStr);
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            Long userId = loginUserInfo.getId();
            BigDecimal[] inAndOut = reportService.getInAndOut(userId, monthFirstStr, monthLastStr);
            BigDecimal in = inAndOut[0] == null ? new BigDecimal(0) : inAndOut[0];
            BigDecimal out = inAndOut[1] == null ? new BigDecimal(0) : inAndOut[1];
            model.addAttribute("in", in);
            model.addAttribute("out", out);
            return "/console/report/monthList";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping("/yearTableManager")
    public String yearTableManager(){
        return "/console/report/yearList";
    }

    @RequestMapping("/inAndOutList")
    @ResponseBody
    public Json inAndOutList(HttpServletRequest request,String year,Model model) {
        Json json = new Json();
        try {
            LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
            Long userId = loginUserInfo.getId();
            Map<String, List<ReportTableVo>> reportTableList = null;
            reportTableList = reportService.getReportTableList(userId, year);
            model.addAttribute("map", reportTableList);
            json.setObj(reportTableList);
            json.setSuccess(true);
            json.setMsg("获取数据成功!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(value = "/MonthBar", method = RequestMethod.GET)
    @ResponseBody
    public Json MonthBar(HttpServletRequest request, String dateStart, String dateEnd, String purposeType, String tallyType) {
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        Long userId = loginUserInfo.getId();
        Json json = new Json();
        json.setSuccess(false);
        json.setMsg("获取数据出错");
        try {
            BarVo monthBar = reportService.getMonthBar(userId, dateStart, dateEnd, Integer.parseInt(purposeType), tallyType);
            json.setMsg("获取数据成功");
            json.setObj(monthBar);
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setMsg(e.getMessage());
        }
        return json;
    }
}
