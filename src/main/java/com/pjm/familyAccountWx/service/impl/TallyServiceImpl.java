package com.pjm.familyAccountWx.service.impl;

import com.pjm.familyAccountWx.common.enu.BizSeqType;
import com.pjm.familyAccountWx.common.util.CalendarUtil;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.common.vo.QueryResult;
import com.pjm.familyAccountWx.dao.AccountDao;
import com.pjm.familyAccountWx.dao.PayUserDao;
import com.pjm.familyAccountWx.dao.PurposeDao;
import com.pjm.familyAccountWx.dao.TallyDao;
import com.pjm.familyAccountWx.model.*;
import com.pjm.familyAccountWx.service.TallyService;
import com.pjm.familyAccountWx.vo.TallyVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
    public void addTally(TallyVo TallyVo, String name, Long userId) throws Exception {
        TTally tTally = new TTally();
        this.copyVoToEntity(TallyVo, tTally);
        tTally.setCreateUser(name);
        tTally.setCreateDate(new Date());
        TUser tUser = tallyDao.find(userId, TUser.class);
        tTally.settUser(tUser);
        tTally.setTallyNo(tallyDao.callBizSeqCode(BizSeqType.TALLY.getSeqType()));
        tallyDao.save(tTally);
    }

    @Override
    public PageModel getTallyList(TallyVo vo, PageModel ph, Long userId) throws Exception {
        List<TallyVo> list = new ArrayList<TallyVo>();
        QueryResult<TTally> pageResult = tallyDao.getTallyList(vo, ph,userId);
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

    @Override
    public void updateTally(TallyVo tallyVo, String name) throws Exception {
        Long id = tallyVo.getId();
        TTally tTally = tallyDao.find(id, TTally.class);
        Date createDate = tTally.getCreateDate();
        String createUser = tTally.getCreateUser();
        this.copyVoToEntity(tallyVo, tTally);
        tTally.setCreateUser(createUser);
        tTally.setCreateDate(createDate);
        tallyDao.update(tTally);
    }

    private void copyVoToEntity(TallyVo tallyVo, TTally tTally) throws Exception {
        BeanUtils.copyProperties(tallyVo, tTally);
        String payDate = tallyVo.getPayDate();
        Long accountId = tallyVo.getAccountId();
        Long purposeId = tallyVo.getPurposeId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate = simpleDateFormat.parse(payDate);
        TAccount tAccount = accountDao.find(accountId, TAccount.class);
        TPurpose tPurpose = purposeDao.find(purposeId, TPurpose.class);
        tTally.settAccount(tAccount);
        tTally.settPurpose(tPurpose);
        tTally.setPayDate(parseDate);
        String payUserIds = tallyVo.getPayUserIds();
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

    private void copyEntityToVo(TTally tally, TallyVo tallyVo) {
        BeanUtils.copyProperties(tally, tallyVo);
        TAccount tAccount = tally.gettAccount();
        Date payDate = tally.getPayDate();
        TPurpose tPurpose = tally.gettPurpose();
        Integer purposeType = tPurpose.getPurposeType();
        Set<TPayUser> tPayUsers = tally.gettPayUserSet();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(payDate);
        tallyVo.setAccountId(tAccount.getId());
        tallyVo.setAccountName(tAccount.getName());
        tallyVo.setPurposeId(tPurpose.getId());
        TPurpose parent = tPurpose.getParent();
        String purposeName = "";
        if (parent != null) {
            String name = parent.getName();
            purposeName += name + " ";
            tallyVo.setParentPurposeId(parent.getId());
        }
        purposeName += tPurpose.getName();
        tallyVo.setPurposeName(purposeName);
        tallyVo.setPayDate(date);
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
            tallyVo.setPayUserIds(payUserIds.deleteCharAt(payUserIds.length() - 1).toString());
            tallyVo.setPayUserNames(payUserNames.deleteCharAt(payUserNames.length() - 1).toString());
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        TTally tTally = tallyDao.find(id, TTally.class);
        tTally.setVisible(false);
        tallyDao.update(tTally);
    }

    @Override
    public void changeStatus(Long id, String status) throws Exception {
        TTally tTally = tallyDao.find(id, TTally.class);
        if (status.equals("-1")) {
            tTally.setVisible(false);
        }else {
            tTally.setVisible(true);
        }
        tallyDao.update(tTally);
    }
}
