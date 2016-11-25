import com.pjm.familyAccountWx.service.TallyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by PanJM on 2016/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class TestPurpose {

    @Resource
    private TallyService tallyService;

    @Test
    public void testGetPurpose() {
        try {
            String purpose = tallyService.queryPurpose("pjmxby");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
