package com.pjm.familyAccountWx.service.impl;

import com.pjm.familyAccountWx.common.PageModel;
import com.pjm.familyAccountWx.common.QueryResult;
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
import com.pjm.familyAccountWx.vo.TallyVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
        this.copyParamToEntity(tallyParam, tTally);
        tallyDao.save(tTally);
    }

    @Override
    public PageModel getTallyList(TallyParam tallyParam, PageModel ph) throws Exception {
        List<TallyVo> list = new ArrayList<TallyVo>();

        QueryResult<TTally> pageResult = tallyDao.getTallyList(tallyParam, ph);
        for (TTally tally : pageResult.getReultList()) {
            TallyVo tallyVo = new TallyVo();
            this.copyEntityToVo(tally, tallyVo);
            list.add(tallyVo);
        }
        if (ph == null) {
            ph = new PageModel();
        }
        ph.setTotal(pageResult.getTotalCount());
        ph.setRows(list);
        return ph;
    }

    @Override
    public TallyVo getTally(Long id) throws Exception {
        TTally tTally = tallyDao.find(id, TTally.class);
        TallyVo tallyVo = new TallyVo();
        this.copyEntityToVo(tTally, tallyVo);
        return tallyVo;
    }

    private void copyEntityToVo(TTally tally, TallyVo tallyVo) {
        Long id = tally.getId();
        BigDecimal money = tally.getMoney();
        TAccount tAccount = tally.gettAccount();
        Date payDate = tally.getPayDate();
        TPurpose tPurpose = tally.gettPurpose();
        Integer purposeType = tPurpose.getPurposeType();
        Set<TPayUser> tPayUsers = tally.gettPayUserSet();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(payDate);
        tallyVo.setMoney(money);
        tallyVo.setAccountId(tAccount.getId());
        tallyVo.setAccountName(tAccount.getName());
        tallyVo.setPurposeId(tPurpose.getId());
        TPurpose parent = tPurpose.getParent();
        String purposeName = "";
        if (parent != null) {
            String name = parent.getName();
            purposeName += name + " ";
        }
        purposeName += tPurpose.getName();
        tallyVo.setPurposeName(purposeName);
        tallyVo.setPayDate(date);
        tallyVo.setId(id);
        tallyVo.setPurposeType(purposeType);
        if (tPayUsers != null && tPayUsers.size() > 0) {
            StringBuffer payUserIds = new StringBuffer("");
            StringBuffer payUserNames = new StringBuffer("");
            for (TPayUser tPayUser : tPayUsers) {
                Long tPayUserId = tPayUser.getId();
                String name = tPayUser.getName();
                payUserIds.append(tPayUserId).append(",");
                payUserNames.append(name).append(",");
            }
            tallyVo.setPayUserIds(payUserIds.deleteCharAt(payUserIds.length()-1).toString());
            tallyVo.setPayUserNames(payUserNames.deleteCharAt(payUserNames.length()-1).toString());
        }
    }

    private void copyParamToEntity(TallyParam tallyParam, TTally tTally) throws Exception {
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
