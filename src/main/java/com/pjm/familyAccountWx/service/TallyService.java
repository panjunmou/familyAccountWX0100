package com.pjm.familyAccountWx.service;

import com.pjm.familyAccountWx.common.PageModel;
import com.pjm.familyAccountWx.vo.TallyParam;
import com.pjm.familyAccountWx.vo.TallyVo;

/**
 * Created by PanJM on 2016/11/22.
 */
public interface TallyService {

    void addTally(TallyParam tallyParam, String name) throws Exception;

    PageModel getTallyList(TallyParam tallyParam, PageModel ph) throws Exception;

    TallyVo getTally(Long id) throws Exception;
}
