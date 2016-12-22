package com.pjm.familyAccountWx.service;

import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.vo.PurposeVo;

/**
 * Created by PanJM on 2016/11/26.
 */
public interface PurposeService {

    String queryPurpose(String userName, Integer purposeType) throws Exception;

    PageModel dataGrid(PurposeVo purposeVo, PageModel ph, String pId) throws Exception;

    void save(PurposeVo purposeVo) throws Exception;

    PurposeVo get(Long id) throws Exception;

    void update(PurposeVo purposeVo) throws Exception;

    void delete(Long id) throws Exception;

    void changeStatus(Long id, String status) throws Exception;
}
