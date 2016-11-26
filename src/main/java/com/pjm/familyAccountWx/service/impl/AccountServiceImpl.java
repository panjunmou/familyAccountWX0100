package com.pjm.familyAccountWx.service.impl;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.dao.AccountDao;
import com.pjm.familyAccountWx.model.TAccount;
import com.pjm.familyAccountWx.service.Accountservice;
import com.pjm.familyAccountWx.vo.SelectVo;
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
}
