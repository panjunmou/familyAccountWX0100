package com.pjm.familyAccountWx.service.impl;

import com.pjm.familyAccountWx.common.vo.Condition;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.common.vo.QueryResult;
import com.pjm.familyAccountWx.dao.UserDao;
import com.pjm.familyAccountWx.model.TUser;
import com.pjm.familyAccountWx.service.UserService;
import com.pjm.familyAccountWx.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/11/19.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public TUser login(String userName, String passWord) throws Exception {
        String md5Hex = DigestUtils.md5Hex(passWord);
        TUser tUser = userDao.queryByNameAndPwd(userName, md5Hex);
        return tUser;
    }

    @Override
    public PageModel dataGrid(UserVo userVo, PageModel ph) throws Exception {
        List<UserVo> list = new ArrayList<UserVo>();
        List<Condition> conList = new ArrayList<>();
        this.fillCondition(userVo, conList);
        QueryResult<TUser> pageResult = userDao.getPageResult(TUser.class, conList, ph);
        for (TUser tUser : pageResult.getReultList()) {
            UserVo vUser = new UserVo();
            this.copyEntityToVo(tUser, vUser);
            list.add(vUser);
        }
        ph.setTotal(pageResult.getTotalCount());
        ph.setRows(list);
        return ph;
    }

    private void copyEntityToVo(TUser tUser, UserVo vUser) {
        BeanUtils.copyProperties(tUser, vUser);
    }

    private void fillCondition(UserVo userVo, List<Condition> conList) {

    }

    @Override
    public void save(UserVo userVo) throws Exception {
        TUser tUser = new TUser();
        this.copyVoToEntity(tUser, userVo);
        userDao.save(tUser);
    }

    private void copyVoToEntity(TUser tUser, UserVo userVo) {
        BeanUtils.copyProperties(userVo, tUser);
    }

    @Override
    public UserVo get(Long id) throws Exception {
        TUser tUser = userDao.find(id, TUser.class);
        UserVo userVo = new UserVo();
        this.copyEntityToVo(tUser, userVo);
        return userVo;
    }

    @Override
    public void update(UserVo userVo) throws Exception {
        Long id = userVo.getId();
        TUser tUser = userDao.find(id, TUser.class);
        this.copyVoToEntity(tUser, userVo);
        userDao.update(tUser);
    }

    @Override
    public void delete(Long id) throws Exception {
        TUser tUser = userDao.find(id, TUser.class);
        userDao.delete(tUser);
    }
}
