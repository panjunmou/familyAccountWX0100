import com.pjm.familyAccountWx.common.service.BaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by PanJM_Levono on 2016/12/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestBaseService {
    @Resource
    private BaseService baseService;

    @Test
    public void testCallBizCodePro() {
        try {
            String tally = baseService.callBizSeqCode("tally");
            System.out.println("tally = " + tally);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
