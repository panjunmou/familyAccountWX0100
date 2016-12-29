package com.pjm.familyAccountWx.dao;

import com.pjm.familyAccountWx.common.dao.BaseDao;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by PanJM_Levono on 2016/12/29.
 */
@Repository
public class ReportDao extends BaseDao {

    public List getMonthBar(String userNo,String dateStart,String dateEnd,Integer purposeType) {
        String sql =
                "SELECT" +
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
        System.out.println("sql = " + sql);
        Query nativeQuery = em.createNativeQuery(sql);
        nativeQuery.setParameter("userNo", userNo);
        nativeQuery.setParameter("dateStart", dateStart);
        nativeQuery.setParameter("dateEnd", dateEnd);
        nativeQuery.setParameter("purposeType", purposeType);
        List resultList = nativeQuery.getResultList();
        return resultList;
    }
}
