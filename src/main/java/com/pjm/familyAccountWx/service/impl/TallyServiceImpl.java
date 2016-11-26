package com.pjm.familyAccountWx.service.impl;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.dao.TallyDao;
import com.pjm.familyAccountWx.model.TPurpose;
import com.pjm.familyAccountWx.service.TallyService;
import com.pjm.familyAccountWx.vo.PurposePicker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PanJM on 2016/11/22.
 */
@Service
public class TallyServiceImpl implements TallyService {

    @Resource
    private TallyDao tallyDao;


}
