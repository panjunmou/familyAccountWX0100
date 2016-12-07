import com.pjm.familyAccountWx.common.util.CalendarUtil;
import org.junit.Test;

/**
 * Created by PanJM_Levono on 2016/12/6.
 */
public class TestCalendarUtil {
    @Test
    public void testYear() {
        String year = CalendarUtil.getYear();
        System.out.println("year = " + year);
    }

    @Test
    public void testMonth() {
        String month = CalendarUtil.getMonth();
        System.out.println("month = " + month);
    }

    @Test
    public void testDay() {
        String day = CalendarUtil.getDay();
        System.out.println("day = " + day);
    }
}
