package com.pjm.familyAccountWx.dao;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PanJM_Levono on 2016/12/29.
 */
@Repository
public class ReportDao extends BaseDao {

    public List getMonthBar(String userNo, String dateStart, String dateEnd, Integer purposeType, String tallyType) {
        String sql = "";

        if (tallyType.equals("purpose")) {
            sql = "SELECT" +
                    "  concat(parent.NAME,'-',p.NAME) as name," +
                    "  sum(MONEY) as money" +
                    " from biz_tally t" +
                    "  LEFT JOIN biz_purpose p on t.PURPOSE_NO = p.PURPOSE_NO" +
                    "  LEFT JOIN biz_purpose parent on p.PARENT_NO = parent.PURPOSE_NO" +
                    " WHERE 1 = 1" +
                    "      AND t.visible = TRUE" +
                    "      AND t.USER_NO =:userNo" +
                    "  AND  DATE_FORMAT( PAYDATE, '%Y-%m-%d') >=:dateStart" +
                    "  AND date_format(PAYDATE,'%Y-%m-%d') <=:dateEnd" +
                    "  AND p.PURPOSE_TYPE =:purposeType" +
                    " GROUP BY t.PURPOSE_NO,p.NAME";
        } else if (tallyType.equals("account")) {
            sql =
                    "SELECT" +
                            "  a.NAME AS name," +
                            "  sum(MONEY)                       AS money" +
                            " from biz_tally t" +
                            "  LEFT JOIN biz_purpose p ON t.PURPOSE_NO = p.PURPOSE_NO" +
                            "  LEFT JOIN biz_purpose parent ON p.PARENT_NO = parent.PURPOSE_NO" +
                            "  LEFT JOIN biz_account a ON t.ACCOUNT_NO = a.ACCOUNT_NO" +
                            " WHERE 1 = 1" +
                            "      AND t.visible = TRUE" +
                            "      AND t.USER_NO =:userNo" +
                            "      AND DATE_FORMAT(PAYDATE, '%Y-%m-%d') >=:dateStart" +
                            "      AND date_format(PAYDATE, '%Y-%m-%d') <=:dateEnd" +
                            "      AND p.PURPOSE_TYPE =:purposeType" +
                            " GROUP BY a.ACCOUNT_NO, a.NAME;";
        } else if (tallyType.equals("payuser")) {
            sql =
                    " SELECT" +
                            "  names," +
                            "  sum(t.MONEY)" +
                            " from (" +
                            "       SELECT" +
                            "         btp.TALLY_NO," +
                            "         group_concat(u.NAME) AS names" +
                            "       from biz_tally_payuser btp" +
                            "         LEFT JOIN biz_payuser u ON u.PAYUSER_NO = btp.PAYUSER_NO" +
                            "       GROUP BY TALLY_NO" +
                            "     ) aaa" +
                            "  LEFT JOIN biz_tally t ON t.TALLY_NO = aaa.TALLY_NO" +
                            "  LEFT JOIN biz_purpose p ON t.PURPOSE_NO = p.PURPOSE_NO" +
                            " WHERE 1 = 1" +
                            "      AND t.visible = TRUE" +
                            "      AND t.USER_NO =:userNo" +
                            "      AND DATE_FORMAT(PAYDATE, '%Y-%m-%d') >=:dateStart" +
                            "      AND date_format(PAYDATE, '%Y-%m-%d') <=:dateEnd" +
                            "      AND p.PURPOSE_TYPE =:purposeType" +
                            " GROUP BY names";
        }
        System.out.println("sql = " + sql);
        Query nativeQuery = em.createNativeQuery(sql);
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("userNo", userNo);
        objectMap.put("dateStart", dateStart);
        objectMap.put("dateEnd", dateEnd);
        objectMap.put("purposeType", purposeType);
        for (String key : objectMap.keySet()) {
            nativeQuery.setParameter(key, objectMap.get(key));
        }
        String jsonString = JSON.toJSONString(objectMap);
        System.out.println("jsonString = " + jsonString);
//        nativeQuery.setParameter("userNo", userNo);
//        nativeQuery.setParameter("dateStart", dateStart);
//        nativeQuery.setParameter("dateEnd", dateEnd);
//        nativeQuery.setParameter("purposeType", purposeType);
        List resultList = nativeQuery.getResultList();
        return resultList;
    }

    public List<Object[]> getInAndOut(String userNo, String dateStart, String dateEnd) {
        String sql =
                "SELECT p.PURPOSE_TYPE,sum(MONEY) AS money" +
                " from biz_tally t" +
                "  LEFT JOIN biz_purpose p ON t.PURPOSE_NO = p.PURPOSE_NO" +
                " wHERE 1 = 1" +
                "      AND t.visible = TRUE" +
                "      AND t.USER_NO =:userNo" +
                "      AND DATE_FORMAT(PAYDATE, '%Y-%m-%d') >=:dateStart" +
                "      AND date_format(PAYDATE, '%Y-%m-%d') <=:dateEnd" +
                " GROUP BY p.PURPOSE_TYPE;";
        System.out.println("sql = " + sql);
        Query nativeQuery = em.createNativeQuery(sql);
        Map<String, Object> objectMap = new HashMap<String, Object>();
        objectMap.put("userNo", userNo);
        objectMap.put("dateStart", dateStart);
        objectMap.put("dateEnd", dateEnd);
        for (String key : objectMap.keySet()) {
            nativeQuery.setParameter(key, objectMap.get(key));
        }
        String jsonString = JSON.toJSONString(objectMap);
        System.out.println("jsonString = " + jsonString);
        List resultList = nativeQuery.getResultList();
        return resultList;
    }
}
