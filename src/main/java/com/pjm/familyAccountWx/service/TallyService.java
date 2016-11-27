package com.pjm.familyAccountWx.service;

import com.pjm.familyAccountWx.common.PageModel;
import com.pjm.familyAccountWx.vo.TallyVo;

/**
 * Created by PanJM on 2016/11/22.
 */
public interface TallyService {

    void addTally(TallyVo TallyVo, String name, Long userId) throws Exception;

    PageModel getTallyList(TallyVo TallyVo, PageModel ph, Long userId) throws Exception;

    TallyVo getTally(Long id) throws Exception;

    void updateTally(TallyVo TallyVo, String name) throws Exception;

    void delete(Long id) throws Exception;
}
