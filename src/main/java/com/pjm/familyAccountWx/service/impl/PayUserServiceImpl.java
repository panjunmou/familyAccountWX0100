package com.pjm.familyAccountWx.service.impl;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.common.vo.Condition;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.common.vo.QueryResult;
import com.pjm.familyAccountWx.dao.PayUserDao;
import com.pjm.familyAccountWx.model.TPayUser;
import com.pjm.familyAccountWx.model.TPayUser;
import com.pjm.familyAccountWx.model.TUser;
import com.pjm.familyAccountWx.service.PayUserService;
import com.pjm.familyAccountWx.vo.PayUserVo;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.PayUserVo;
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
public class PayUserServiceImpl implements PayUserService {

    @Resource
    private PayUserDao payUserDao;

    @Override
    public String queryPayUserListByUserId(Long id) throws Exception {
        List<TPayUser> tUserList = payUserDao.getPayUserByUserId(id);
        List<SelectVo> selectVoList = new ArrayList<SelectVo>();
        if (tUserList != null && tUserList.size() > 0) {
            for (int i = 0; i < tUserList.size(); i++) {
                TPayUser tPayUser = tUserList.get(i);
                String userName = tPayUser.getName();
                Long tUserId = tPayUser.getId();
                SelectVo selectVo = new SelectVo();
                selectVo.setValue(tUserId.toString());
                selectVo.setTitle(userName);
                selectVoList.add(selectVo);
            }
        }
        String jsonString = JSON.toJSONString(selectVoList);
        return jsonString;
    }

    @Override
    public PageModel dataGrid(PayUserVo payUserVo, PageModel ph) throws Exception {
        List<PayUserVo> list = new ArrayList<PayUserVo>();
        List<Condition> conList = new ArrayList<>();
        this.fillCondition(payUserVo, conList);
        ph.setSort("seq");
        ph.setOrder("asc");
        QueryResult<TPayUser> pageResult = payUserDao.getPageResult(TPayUser.class, conList, ph);
        for (TPayUser tPayUser : pageResult.getReultList()) {
            PayUserVo userVo = new PayUserVo();
            this.copyEntityToVo(tPayUser, userVo);
            list.add(userVo);
        }
        ph.setTotal(pageResult.getTotalCount());
        ph.setRows(list);
        return ph;
    }

    private void copyEntityToVo(TPayUser tPayUser, PayUserVo userVo) {
        BeanUtils.copyProperties(tPayUser, userVo);
        userVo.setUserName(tPayUser.gettUser().getUserName());
        userVo.setUserNo(tPayUser.gettUser().getUserNo());
    }

    private void fillCondition(PayUserVo payUserVo, List<Condition> conList) {
        LoginUserInfo loginUserInfo = payUserVo.getLoginUserInfo();
        Long id = loginUserInfo.getId();
        conList.add(new Condition("tUser.id", id, Condition.EQUAL_TO));
    }

    @Override
    public void save(PayUserVo payUserVo) throws Exception {
        TPayUser tPayUser = new TPayUser();
        this.copyVoToEntity(tPayUser, payUserVo);
        payUserDao.save(tPayUser);
    }

    private void copyVoToEntity(TPayUser tPayUser, PayUserVo payUserVo) throws Exception {
        BeanUtils.copyProperties(payUserVo, tPayUser);
        LoginUserInfo loginUserInfo = payUserVo.getLoginUserInfo();
        Long id = loginUserInfo.getId();
        TUser tUser = payUserDao.find(id, TUser.class);
        tPayUser.settUser(tUser);
    }

    @Override
    public PayUserVo get(Long id) throws Exception {
        TPayUser tPayUser = payUserDao.find(id, TPayUser.class);
        PayUserVo payUserVo = new PayUserVo();
        this.copyEntityToVo(tPayUser, payUserVo);
        return payUserVo;
    }

    @Override
    public void update(PayUserVo payUserVo) throws Exception {
        Long id = payUserVo.getId();
        TPayUser tPayUser = payUserDao.find(id, TPayUser.class);
        this.copyVoToEntity(tPayUser, payUserVo);
        payUserDao.update(tPayUser);
    }

    @Override
    public void delete(Long id) throws Exception {
        TPayUser tPayUser = payUserDao.find(id, TPayUser.class);
        payUserDao.delete(tPayUser);
    }
}
