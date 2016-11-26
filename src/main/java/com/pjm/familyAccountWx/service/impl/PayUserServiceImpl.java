package com.pjm.familyAccountWx.service.impl;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.dao.PayUserDao;
import com.pjm.familyAccountWx.model.TPayUser;
import com.pjm.familyAccountWx.model.TUser;
import com.pjm.familyAccountWx.service.PayUserService;
import com.pjm.familyAccountWx.vo.SelectVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/11/26.
 */
@Service
public class PayUserServiceImpl implements PayUserService {

    @Resource
    private PayUserDao payUserDao;

    @Override
    public String queryPayUserListByUserId(Long id) throws Exception {
        List<TPayUser> tUserList = payUserDao.getPayUserByUserId(id);
        List<SelectVo> selectVoList = new ArrayList<SelectVo>();
        if (tUserList != null && tUserList.size() > 0) {
            for (int i = 0; i < tUserList.size(); i++) {
                TPayUser tPayUser = tUserList.get(i);
                String userName = tPayUser.getName();
                Long tUserId = tPayUser.getId();
                SelectVo selectVo = new SelectVo();
                selectVo.setValue(tUserId.toString());
                selectVo.setTitle(userName);
                selectVoList.add(selectVo);
            }
        }
        String jsonString = JSON.toJSONString(selectVoList);
        return jsonString;
    }
}
