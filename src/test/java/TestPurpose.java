import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.service.PurposeService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.PurposePicker;
import com.pjm.familyAccountWx.vo.PurposeVo;
import com.pjm.familyAccountWx.vo.SelectVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestPurpose {

    @Resource
    private PurposeService purposeService;

    @Test
    public void test() {
        try {
            PurposeVo purposeVo = new PurposeVo();
            LoginUserInfo loginUserInfo = new LoginUserInfo();
            loginUserInfo.setId(1l);
            purposeVo.setLoginUserInfo(loginUserInfo);
            PageModel pageModel = new PageModel();
            pageModel.setPageSize(10);
            pageModel.setPage(1);
            PageModel pageModel1 = purposeService.dataGrid(purposeVo, pageModel, null);
            System.out.println("pageModel1 = " + pageModel1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        String purposeInList = null;
        try {
            purposeInList = purposeService.queryPurpose("pjmxby", -1);
            List<PurposePicker> purposePickerList = JSON.parseObject(purposeInList, new TypeReference<ArrayList<PurposePicker>>(){});
            System.out.println("TestPurpose.test2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
