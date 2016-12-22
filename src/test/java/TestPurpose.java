import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.service.PurposeService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.PurposeVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
}
