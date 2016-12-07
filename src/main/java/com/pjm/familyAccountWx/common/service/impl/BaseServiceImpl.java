package com.pjm.familyAccountWx.common.service.impl;

import com.pjm.familyAccountWx.common.dao.BaseDao;
import com.pjm.familyAccountWx.common.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by PanJM_Levono on 2016/12/7.
 */
@Service
public class BaseServiceImpl implements BaseService{

    @Resource
    private BaseDao baseDao;

    @Override
    public String callBizSeqCode(String seqType) throws Exception {
        System.out.println("BaseServiceImpl.callBizSeqCode");
        return baseDao.callBizSeqCode(seqType);
    }
}
