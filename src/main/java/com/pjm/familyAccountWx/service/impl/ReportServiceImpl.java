package com.pjm.familyAccountWx.service.impl;

import com.pjm.familyAccountWx.dao.ReportDao;
import com.pjm.familyAccountWx.model.TUser;
import com.pjm.familyAccountWx.service.ReportService;
import com.pjm.familyAccountWx.vo.BarVo;
import com.pjm.familyAccountWx.vo.ReportDataVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by PanJM_Levono on 2016/12/29.
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportDao reportDao;

    @Override
    public BarVo getMonthBar(Long userId, String dateStart, String dateEnd, Integer purposeType, String tallyType) throws Exception{
        TUser tUser = reportDao.find(userId, TUser.class);
        String userNo = tUser.getUserNo();
        List monthBarList = reportDao.getMonthBar(userNo, dateStart, dateEnd, purposeType,tallyType);
        List<String> legendDatas = new ArrayList<>();
        List<ReportDataVo> reportDataVoList = new ArrayList<>();
        BarVo barVo = new BarVo();
        if (monthBarList != null && monthBarList.size() > 0) {
            for (int i = 0; i < monthBarList.size(); i++) {
                Object[] object = (Object[]) monthBarList.get(i);
                String name = (String) object[0];
                BigDecimal money = (BigDecimal) object[1];
                legendDatas.add(name);
                ReportDataVo reportDataVo = new ReportDataVo();
                reportDataVo.setName(name);
                reportDataVo.setValue(String.valueOf(money));
                reportDataVoList.add(reportDataVo);
            }
            barVo.setLegendDatas(legendDatas);
            barVo.setSeriesDatas(reportDataVoList);
        }
        return barVo;
    }

    @Override
    public BigDecimal[] getInAndOut(Long userId, String monthFirstStr, String monthLastStr) throws Exception {
        //查询总收入和支出,数组长度总共为2,第一个为总收入,第二个为总支出
        TUser tUser = reportDao.find(userId, TUser.class);
        String userNo = tUser.getUserNo();
        List<Object[]> objects = reportDao.getInAndOut(userNo, monthFirstStr, monthLastStr);
        BigDecimal[] bigDecimals = new BigDecimal[2];
        if (objects != null && objects.size() > 0) {
            for (int i = 0; i < objects.size(); i++) {
                Object[] object = (Object[]) objects.get(i);
                Integer purposeType = (Integer) object[0];
                BigDecimal money = (BigDecimal) object[1];
                if (purposeType == -1) {
                    bigDecimals[1] = money;
                }else{
                    bigDecimals[0] = money;
                }
            }
        }else{
            bigDecimals[0] = new BigDecimal(0);
            bigDecimals[1] = new BigDecimal(0);
        }
        return bigDecimals;
    }
}
