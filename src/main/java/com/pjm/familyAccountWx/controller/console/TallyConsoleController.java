package com.pjm.familyAccountWx.controller.console;

import com.pjm.familyAccountWx.common.controller.BaseController;
import com.pjm.familyAccountWx.common.enu.BizSeqType;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.service.TallyService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.TallyVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by PanJM_Levono on 2016/12/14.
 */
@Controller
@RequestMapping("/console/tally")
public class TallyConsoleController extends BaseController {

    @Resource
    private TallyService tallyService;

    @RequestMapping("/manager")
    public String manager() throws Exception {
        return "/console/tally/tallyList";
    }

    @RequestMapping("/dataGrid")
    @ResponseBody
    public PageModel dataGrid(TallyVo tallyVo, PageModel ph, HttpServletRequest request) {
        LoginUserInfo loginUserInfo = this.getLoginUserInfo(request);
        try {
            Long userId = loginUserInfo.getId();
            PageModel pageModel = tallyService.getTallyList(tallyVo, ph, userId);
            return pageModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ph;
    }

    @RequestMapping("/addPage")
    public String addPage(Model model) throws Exception {
        String tallyNo = this.getBizSeqCode(BizSeqType.TALLY.getSeqType());
        model.addAttribute("tallyNo", tallyNo);
        return "/console/tally/tallyAdd";
    }

    @RequestMapping("/editPage")
    public String updatePage(Model model, Long id) throws Exception {
        TallyVo tallyVo = tallyService.getTally(id);
        model.addAttribute("tally", tallyVo);
        return "/console/tally/tallyEdit";
    }
}
