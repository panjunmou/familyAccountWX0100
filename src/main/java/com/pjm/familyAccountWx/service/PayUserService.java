package com.pjm.familyAccountWx.service;

import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.vo.PayUserVo;

/**
 * Created by PanJM on 2016/11/26.
 */
public interface PayUserService {
    String queryPayUserListByUserId(Long id) throws Exception;

    PageModel dataGrid(PayUserVo payUserVo, PageModel ph) throws Exception;

    void save(PayUserVo payUserVo) throws Exception;

    PayUserVo get(Long id) throws Exception;

    void update(PayUserVo payUserVo) throws Exception;

    void delete(Long id) throws Exception;
}
