package com.pjm.familyAccountWx.service;

import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.vo.AccountVo;

/**
 * Created by PanJM on 2016/11/26.
 */
public interface Accountservice {
    String queryAccountListByUserId(Long id) throws Exception;

    PageModel dataGrid(AccountVo accountVo, PageModel ph) throws Exception;

    void save(AccountVo accountVo) throws Exception;

    AccountVo get(Long id) throws Exception;

    void update(AccountVo accountVo) throws Exception;

    void delete(Long id) throws Exception;
}
