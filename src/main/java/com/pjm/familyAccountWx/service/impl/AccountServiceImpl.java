package com.pjm.familyAccountWx.service.impl;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.common.vo.Condition;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.common.vo.QueryResult;
import com.pjm.familyAccountWx.dao.AccountDao;
import com.pjm.familyAccountWx.model.TAccount;
import com.pjm.familyAccountWx.model.TUser;
import com.pjm.familyAccountWx.service.Accountservice;
import com.pjm.familyAccountWx.vo.AccountVo;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.SelectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/11/26.
 */
@Service
public class AccountServiceImpl implements Accountservice {

    @Resource
    private AccountDao accountDao;

    @Override
    public String queryAccountListByUserId(Long id) throws Exception {
        List<TAccount> accountList = accountDao.getAccountListByUserId(id);
        List<SelectVo> selectVoList = new ArrayList<SelectVo>();
        if (accountList != null && accountList.size() > 0) {
            for (int i = 0; i < accountList.size(); i++) {
                TAccount tAccount = accountList.get(i);
                String name = tAccount.getName();
                Long tAccountId = tAccount.getId();
                SelectVo selectVo = new SelectVo();
                selectVo.setTitle(name);
                selectVo.setValue(tAccountId.toString());
                selectVoList.add(selectVo);
            }
        }
        String jsonString = JSON.toJSONString(selectVoList);
        return jsonString;
    }

    @Override
    public PageModel dataGrid(AccountVo accountVo, PageModel ph) throws Exception {
        List<AccountVo> list = new ArrayList<AccountVo>();
        List<Condition> conList = new ArrayList<>();
        this.fillCondition(accountVo, conList);
        ph.setSort("seq");
        ph.setOrder("asc");
        QueryResult<TAccount> pageResult = accountDao.getPageResult(TAccount.class, conList, ph);
        for (TAccount tAccount : pageResult.getReultList()) {
            AccountVo account = new AccountVo();
            this.copyEntityToVo(tAccount, account);
            list.add(account);
        }
        ph.setTotal(pageResult.getTotalCount());
        ph.setRows(list);
        return ph;
    }

    private void copyEntityToVo(TAccount tAccount, AccountVo account) {
        BeanUtils.copyProperties(tAccount, account);
        account.setUserName(tAccount.gettUser().getUserName());
        account.setUserNo(tAccount.gettUser().getUserNo());
    }

    private void fillCondition(AccountVo accountVo, List<Condition> conList) {
        LoginUserInfo loginUserInfo = accountVo.getLoginUserInfo();
        Long id = loginUserInfo.getId();
        conList.add(new Condition("tUser.id", id, Condition.EQUAL_TO));
    }

    @Override
    public void save(AccountVo accountVo) throws Exception {
        TAccount tAccount = new TAccount();
        this.copyVoToEntity(tAccount, accountVo);
        accountDao.save(tAccount);
    }

    private void copyVoToEntity(TAccount tAccount, AccountVo accountVo) throws Exception {
        BeanUtils.copyProperties(accountVo, tAccount);
        LoginUserInfo loginUserInfo = accountVo.getLoginUserInfo();
        Long id = loginUserInfo.getId();
        TUser tUser = accountDao.find(id, TUser.class);
        tAccount.settUser(tUser);
    }

    @Override
    public AccountVo get(Long id) throws Exception {
        TAccount tAccount = accountDao.find(id, TAccount.class);
        AccountVo accountVo = new AccountVo();
        this.copyEntityToVo(tAccount, accountVo);
        return accountVo;
    }

    @Override
    public void update(AccountVo accountVo) throws Exception {
        Long id = accountVo.getId();
        TAccount tAccount = accountDao.find(id, TAccount.class);
        this.copyVoToEntity(tAccount, accountVo);
        accountDao.update(tAccount);
    }

    @Override
    public void delete(Long id) throws Exception {
        TAccount tAccount = accountDao.find(id, TAccount.class);
        accountDao.delete(tAccount);
    }
}
