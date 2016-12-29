import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.dao.ReportDao;
import com.pjm.familyAccountWx.service.ReportService;
import com.pjm.familyAccountWx.vo.BarVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by PanJM_Levono on 2016/12/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class TestReport {

    @Resource
    private ReportDao reportDao;

    @Resource
    private ReportService reportService;

    @Test
    public void testMonthBar() {
        try {
            BarVo barVo = reportService.getMonthBar(1l, "2016-12-01", "2016-12-31", -1);
            System.out.println("barVo.getLegendDatas() = " + JSON.toJSONString(barVo.getLegendDatas()));
            System.out.println("barVo.getSeriesDatas() = " + JSON.toJSONString(barVo.getSeriesDatas()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
