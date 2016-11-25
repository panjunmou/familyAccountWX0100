package com.pjm.familyAccountWx.service.impl;

import com.pjm.familyAccountWx.dao.UserDao;
import com.pjm.familyAccountWx.model.TUser;
import com.pjm.familyAccountWx.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by PanJM on 2016/11/19.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    public boolean login(String userName, String passWord) throws Exception {
        String md5Hex = DigestUtils.md5Hex(passWord);
        TUser tUser = userDao.queryByNameAndPwd(userName,md5Hex);
        if (tUser == null) {
            return false;
        } else {
            return true;
        }
    }
}
