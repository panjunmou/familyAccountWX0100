package com.pjm.familyAccountWx.controller.console;

import com.pjm.familyAccountWx.common.controller.BaseController;
import com.pjm.familyAccountWx.common.vo.Json;
import com.pjm.familyAccountWx.service.ReportService;
import com.pjm.familyAccountWx.vo.BarVo;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM_Levono on 2016/12/28.
 */
@Controller
@RequestMapping("/console/report")
public class ReportController extends BaseController {

    @Resource
    private ReportService reportService;

    @RequestMapping("/monthManager")
    public String monthManager() throws Exception {
        return "/console/report/monthList";
    }

    @RequestMapping("/yearManager")
    public String yearManager() throws Exception {
        return "/console/report/yearList";
    }

    @RequestMapping(value = "/MonthBar", method = RequestMethod.GET)
    @ResponseBody
    public Json MonthBar(HttpServletRequest request,String dateStart,String dateEnd,String purposeType) {
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        Long userId = loginUserInfo.getId();
        Json json = new Json();
        json.setSuccess(false);
        json.setMsg("获取数据出错");
        try {
            BarVo monthBar = reportService.getMonthBar(userId, "2016-12-01", "2016-12-31", -1);
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
