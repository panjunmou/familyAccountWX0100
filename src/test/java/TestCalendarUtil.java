import com.pjm.familyAccountWx.common.util.DateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by PanJM_Levono on 2016/12/6.
 */
public class TestCalendarUtil {

    /**
     * 把日期转换为字符串
     */
    @Test
    public void testDateToString() {
        String dateToString = DateUtil.dateToString(new Date(), DateUtil.FORMAT_DATE_TIME);
        System.out.println("dateToString = " + dateToString);
    }

    /**
     * 把字符串转换为日期
     */
    @Test
    public void testStringToDate() {
        Date date = DateUtil.stringToDate("2016-12-30 00:00:00", DateUtil.FORMAT_DATE_TIME);
        System.out.println("date = " + date);
    }

    /**
     * 获取两个日期之间的天数
     */
    @Test
    public void testGetBetweenDay() {
        Date date1 = DateUtil.stringToDate("2016-12-25 23:00:00", DateUtil.FORMAT_DATE_TIME);
        Date date2 = DateUtil.stringToDate("2016-12-30 00:00:00", DateUtil.FORMAT_DATE_TIME);
        int betweenDay = DateUtil.getBetweenDay(date1, date2);
        System.out.println("betweenDay = " + betweenDay);
    }

    /**
     * 获取两个日期之间的年数
     */
    @Test
    public void testGetBetweenYear() {
        Date date1 = DateUtil.stringToDate("2015-12-25 23:00:00", DateUtil.FORMAT_DATE_TIME);
        Date date2 = DateUtil.stringToDate("2016-12-30 00:00:00", DateUtil.FORMAT_DATE_TIME);
        int betweenYear = DateUtil.getBetweenYear(date1, date2);
        System.out.println("betweenYear = " + betweenYear);
    }

    @Test
    public void testYear() {
        Date date1 = DateUtil.stringToDate("2015-12-25 23:00:00", DateUtil.FORMAT_DATE_TIME);
        int year = DateUtil.getYear(date1);
        System.out.println("year = " + year);
    }

    @Test
    public void testMonth() {
        Date date1 = DateUtil.stringToDate("2015-11-25 23:00:00", DateUtil.FORMAT_DATE_TIME);
        int month = DateUtil.getMonth(date1);
        System.out.println("month = " + month);
    }

    @Test
    public void testDay() {
        Date date1 = DateUtil.stringToDate("2015-11-25 23:00:00", DateUtil.FORMAT_DATE_TIME);
        int day = DateUtil.getDay(date1);
        System.out.println("day = " + day);
    }

    @Test
    public void testGetMonthDay() throws ParseException {
        Date date1 = DateUtil.stringToDate("2012-02-25 23:00:00", DateUtil.FORMAT_DATE_TIME);
        int day = DateUtil.getMonthDay(date1);
        System.out.println("day = " + day);
    }
}
