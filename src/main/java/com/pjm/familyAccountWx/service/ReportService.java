package com.pjm.familyAccountWx.service;

import com.pjm.familyAccountWx.vo.BarVo;

/**
 * Created by PanJM on 2016/11/19.
 */
public interface ReportService {

    BarVo getMonthBar(Long userId, String dateStart, String dateEnd, Integer purposeType, String tallyType) throws Exception;
}
