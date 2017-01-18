package com.pjm.familyAccountWx.service.impl;

import com.pjm.familyAccountWx.common.util.DateUtil;
import com.pjm.familyAccountWx.dao.ReportDao;
import com.pjm.familyAccountWx.model.TUser;
import com.pjm.familyAccountWx.service.ReportService;
import com.pjm.familyAccountWx.vo.BarVo;
import com.pjm.familyAccountWx.vo.ReportDataVo;
import com.pjm.familyAccountWx.vo.ReportTableVo;
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

    @Override
    public void getReportTableList(Long userId, String year) throws Exception {
        TUser tUser = reportDao.find(userId, TUser.class);
        List<Object[]> reportTableList = reportDao.getReportTableList(tUser.getUserNo(), year);
        Map<String, ReportTableVo> inMap = new HashMap<>();
        Map<String, ReportTableVo> outMap = new HashMap<>();
        if (reportTableList != null && reportTableList.size() > 0) {
            for (int i = 0; i < reportTableList.size(); i++) {
                Object[] objects = reportTableList.get(i);
                String monthStr = (String) objects[0];
                BigDecimal money = (BigDecimal) objects[1];
                Integer purposeType = (Integer) objects[2];
                String purposeName = (String) objects[3];
                String purposeNo = (String) objects[4];
                Integer month = Integer.parseInt(monthStr);
                ReportTableVo reportTableVo = null;
                if (purposeType == -1) {
                    //支出
                    reportTableVo = outMap.get(purposeNo);
                }else {
                    //收入
                    reportTableVo = inMap.get(purposeNo);
                }
                if (reportTableVo == null) {
                    reportTableVo = new ReportTableVo();
                }
                reportTableVo.setPurposeNo(purposeNo);
                reportTableVo.setPurposeName(purposeName);
                BigDecimal[] moneyArr = reportTableVo.getMoney();
                if (moneyArr == null) {
                    moneyArr = new BigDecimal[12];
                }
                if (money == null) {
                    money = new BigDecimal(0);
                }
                moneyArr[month-1] = money;
                reportTableVo.setMoney(moneyArr);
                if (purposeType == -1) {
                    //支出
                    outMap.put(purposeNo,reportTableVo);
                }else {
                    //收入
                    inMap.put(purposeNo,reportTableVo);
                }
            }
        }
        if (outMap != null && outMap.size() > 0) {
            for (String key : outMap.keySet()) {
                ReportTableVo reportTableVo = outMap.get(key);
                BigDecimal[] moneyArr = reportTableVo.getMoney();
                if (moneyArr != null && moneyArr.length > 0) {
                    for (int i = 0; i < moneyArr.length; i++) {
                        BigDecimal money = moneyArr[i];
                        if (money == null) {
                            moneyArr[i] = new BigDecimal(0);
                        }
                    }
                    reportTableVo.setMoney(moneyArr);
                    outMap.put(reportTableVo.getPurposeNo(), reportTableVo);
                }
            }
        }
        if (inMap != null && inMap.size() > 0) {
            for (String key : inMap.keySet()) {
                ReportTableVo reportTableVo = inMap.get(key);
                BigDecimal[] moneyArr = reportTableVo.getMoney();
                if (moneyArr != null && moneyArr.length > 0) {
                    for (int i = 0; i < moneyArr.length; i++) {
                        BigDecimal money = moneyArr[i];
                        if (money == null) {
                            moneyArr[i] = new BigDecimal(0);
                        }
                    }
                    reportTableVo.setMoney(moneyArr);
                    inMap.put(reportTableVo.getPurposeNo(), reportTableVo);
                }
            }
        }
        System.out.println("ReportServiceImpl.getReportTableList");
    }
}
