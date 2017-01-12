package com.pjm.familyAccountWx.controller.console;

import com.pjm.familyAccountWx.common.controller.BaseController;
import com.pjm.familyAccountWx.common.util.DateUtil;
import com.pjm.familyAccountWx.common.vo.Json;
import com.pjm.familyAccountWx.service.ReportService;
import com.pjm.familyAccountWx.vo.BarVo;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by PanJM_Levono on 2016/12/28.
 */
@Controller
@RequestMapping("/console/report")
public class ReportController extends BaseController {

    @Resource
    private ReportService reportService;

    @RequestMapping("/monthManager")
    public String monthManager(Model model) throws Exception {
        String monthFirstStr = DateUtil.getMonthFirstStr(new Date());
        String monthLastStr = DateUtil.getMonthLastStr(new Date());
        model.addAttribute("dateStart", monthFirstStr);
        model.addAttribute("dateEnd", monthLastStr);
        return "/console/report/monthList";
    }

    @RequestMapping("/yearManager")
    public String yearManager() throws Exception {
        return "/console/report/yearList";
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
