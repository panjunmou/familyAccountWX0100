package com.pjm.familyAccountWx.service;

/**
 * Created by PanJM on 2016/11/19.
 */
public interface UserService {
    boolean login(String userName, String passWord) throws Exception;
}
