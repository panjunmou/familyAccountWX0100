package com.pjm.familyAccountWx.service;

import com.pjm.familyAccountWx.model.TUser;

/**
 * Created by PanJM on 2016/11/19.
 */
public interface UserService {
    TUser login(String userName, String passWord) throws Exception;
}
