package com.pjm.familyAccountWx.service;

import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.model.TUser;
import com.pjm.familyAccountWx.vo.UserVo;

/**
 * Created by PanJM on 2016/11/19.
 */
public interface UserService {
    TUser login(String userName, String passWord) throws Exception;

    PageModel dataGrid(UserVo userVo, PageModel ph) throws Exception;

    void save(UserVo userVo) throws Exception;

    UserVo get(Long id) throws Exception;

    void update(UserVo userVo) throws Exception;

    void delete(Long id) throws Exception;
}
