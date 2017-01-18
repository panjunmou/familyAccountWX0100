package com.pjm.familyAccountWx.service;

import com.pjm.familyAccountWx.vo.BarVo;

import java.math.BigDecimal;

/**
 * Created by PanJM on 2016/11/19.
 */
public interface ReportService {

    BarVo getMonthBar(Long userId, String dateStart, String dateEnd, Integer purposeType, String tallyType) throws Exception;

    BigDecimal[] getInAndOut(Long userId, String monthFirstStr, String monthLastStr) throws Exception;

    void getReportTableList(Long userId, String year) throws Exception;
}
