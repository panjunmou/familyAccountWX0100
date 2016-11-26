package com.pjm.familyAccountWx.service.impl;

import com.pjm.familyAccountWx.dao.AccountDao;
import com.pjm.familyAccountWx.dao.PayUserDao;
import com.pjm.familyAccountWx.dao.PurposeDao;
import com.pjm.familyAccountWx.dao.TallyDao;
import com.pjm.familyAccountWx.model.TAccount;
import com.pjm.familyAccountWx.model.TPayUser;
import com.pjm.familyAccountWx.model.TPurpose;
import com.pjm.familyAccountWx.model.TTally;
import com.pjm.familyAccountWx.service.TallyService;
import com.pjm.familyAccountWx.vo.TallyParam;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PanJM on 2016/11/22.
 */
@Service
public class TallyServiceImpl implements TallyService {

    @Resource
    private TallyDao tallyDao;
    @Resource
    private AccountDao accountDao;
    @Resource
    private PurposeDao purposeDao;
    @Resource
    private PayUserDao payUserDao;

    @Override
    public void addTally(TallyParam tallyParam, String name) throws Exception {
        TTally tTally = new TTally();
        tTally.setCreateUser(name);
        tTally.setCreateDate(new Date());
        this.copyVoToEntity(tallyParam, tTally);
        tallyDao.save(tTally);
    }

    private void copyVoToEntity(TallyParam tallyParam, TTally tTally) throws Exception {
        String tabId = tallyParam.getTabId();
        BigDecimal money = tallyParam.getMoney();
        String payDate = tallyParam.getPayDate();
        Long accountId = tallyParam.getAccountId();
        Long purposeId = tallyParam.getPurposeId();
        String remark = tallyParam.getRemark();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate = simpleDateFormat.parse(payDate);
        TAccount tAccount = accountDao.find(accountId, TAccount.class);
        TPurpose tPurpose = purposeDao.find(purposeId, TPurpose.class);
        tTally.setMoney(money);
        tTally.settAccount(tAccount);
        tTally.settPurpose(tPurpose);
        tTally.setPayDate(parseDate);
        tTally.setRemark(remark);
        if (tabId.equalsIgnoreCase("tabIn")) {
            //支出
            String payUserIds = tallyParam.getPayUserIds();
            if (!StringUtils.isEmpty(payUserIds)) {
                String[] split = payUserIds.split(",");
                Set<TPayUser> tPayUserSet = new HashSet<TPayUser>();
                for (String idStr : split) {
                    Long payUserId = Long.parseLong(idStr);
                    TPayUser tPayUser = payUserDao.find(payUserId, TPayUser.class);
                    tPayUserSet.add(tPayUser);
                }
                tTally.settPayUserSet(tPayUserSet);
            }
        }
    }
}
