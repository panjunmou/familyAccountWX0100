package com.pjm.familyAccountWx.service;

import com.pjm.familyAccountWx.vo.BarVo;
import com.pjm.familyAccountWx.vo.ReportTableVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by PanJM on 2016/11/19.
 */
public interface ReportService {

    BarVo getMonthBar(Long userId, String dateStart, String dateEnd, Integer purposeType, String tallyType) throws Exception;

    BigDecimal[] getInAndOut(Long userId, String monthFirstStr, String monthLastStr) throws Exception;

    Map<String, List<ReportTableVo>> getReportTableList(Long userId, String year) throws Exception;

    List<ReportTableVo> getChildrenTableList(Long userId, String year, String parentPurposeNo) throws Exception;
}
