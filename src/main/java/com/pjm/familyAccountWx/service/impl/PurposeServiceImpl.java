package com.pjm.familyAccountWx.service.impl;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.common.vo.Condition;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.common.vo.QueryResult;
import com.pjm.familyAccountWx.dao.PurposeDao;
import com.pjm.familyAccountWx.model.TPurpose;
import com.pjm.familyAccountWx.model.TUser;
import com.pjm.familyAccountWx.service.PurposeService;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import com.pjm.familyAccountWx.vo.PurposePicker;
import com.pjm.familyAccountWx.vo.PurposeVo;
import com.pjm.familyAccountWx.vo.SelectVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by PanJM on 2016/11/26.
 */
@Service
public class PurposeServiceImpl implements PurposeService {

    @Resource
    private PurposeDao purposeDao;

    @Override
    public String queryPurpose(String userName, Integer purposeType) throws Exception {
        List<TPurpose> tPurposeList = purposeDao.queryPurpose(userName, purposeType);
        Map<Long, TPurpose> tPurposeMap = new HashMap<Long, TPurpose>();
        Map<Long, PurposePicker> purposePickerMap = new HashMap<Long, PurposePicker>();
        List<PurposePicker> purposePickerList = new ArrayList<PurposePicker>();
        if (tPurposeList != null && tPurposeList.size() > 0) {
            for (int i = 0; i < tPurposeList.size(); i++) {
                TPurpose tPurpose = tPurposeList.get(i);
                Long id = tPurpose.getId();
                tPurposeMap.put(id, tPurpose);
            }
            for (int i = 0; i < tPurposeList.size(); i++) {
                TPurpose tPurpose = tPurposeList.get(i);
                Long id = tPurpose.getId();
                PurposePicker purposePicker = purposePickerMap.get(id);
                if (purposePicker == null) {
                    purposePicker = new PurposePicker();
                    copyPurposeToPicker(tPurpose, purposePicker);
                    TPurpose parent = tPurpose.getParent();
                    if (parent == null) {
                        purposePickerMap.put(id, purposePicker);
                    } else {
                        boolean visible = parent.isVisible();
                        if (visible) {
                            Long parentId = parent.getId();
                            PurposePicker parentPurposePicker = purposePickerMap.get(parentId);
                            if (parentPurposePicker == null) {
                                parentPurposePicker = new PurposePicker();
                                copyPurposeToPicker(parent, parentPurposePicker);
                            }
                            List<PurposePicker> sub = parentPurposePicker.getSub();
                            if (sub == null) {
                                sub = new ArrayList<PurposePicker>();
                            }
                            sub.add(purposePicker);
                            parentPurposePicker.setSub(sub);
                            purposePickerMap.put(parentId, parentPurposePicker);
                        }
                    }
                }
            }
            for (Long purposeId : purposePickerMap.keySet()) {
                PurposePicker purposePicker = purposePickerMap.get(purposeId);
                purposePickerList.add(purposePicker);
            }
        }
        String jsonString = JSON.toJSONString(purposePickerList);
        return jsonString;
    }

    @Override
    public List<SelectVo> queryPurposeByPid(String userName, Integer purposeType, Integer parentId) throws Exception {
        List<TPurpose> tPurposeList = purposeDao.queryPurposeByPId(userName, purposeType, parentId);
        List<SelectVo> selectVoList = new ArrayList<>();
        if (tPurposeList != null && tPurposeList.size() > 0) {
            for (int i = 0; i < tPurposeList.size(); i++) {
                TPurpose tPurpose = tPurposeList.get(i);
                SelectVo selectVo = new SelectVo();
                selectVo.setTitle(tPurpose.getName());
                selectVo.setValue(tPurpose.getId().toString());
            }
        }
        return selectVoList;
    }

    private void copyPurposeToPicker(TPurpose tPurpose, PurposePicker purposePicker) {
        purposePicker.setId(tPurpose.getId());
        purposePicker.setName(tPurpose.getName());
        TPurpose parent = tPurpose.getParent();
        if (parent == null) {
            purposePicker.setType("0");
        }
    }

    @Override
    public PageModel dataGrid(PurposeVo purposeVo, PageModel ph, String pId) throws Exception {
        List<PurposeVo> list = new ArrayList<PurposeVo>();
        List<Condition> conList = new ArrayList<>();
        this.fillCondition(purposeVo, conList, pId);
        ph.setSort("purposeType,seq");
        ph.setOrder("asc,asc");
        QueryResult<TPurpose> pageResult = purposeDao.getPageResult(TPurpose.class, conList, ph);
        for (TPurpose tPayUser : pageResult.getReultList()) {
            PurposeVo userVo = new PurposeVo();
            this.copyEntityToVo(tPayUser, userVo);
            list.add(userVo);
        }
        ph.setTotal(pageResult.getTotalCount());
        ph.setRows(list);
        return ph;
    }

    private void copyEntityToVo(TPurpose tPurpose, PurposeVo userVo) {
        BeanUtils.copyProperties(tPurpose, userVo);
        userVo.setUserName(tPurpose.gettUser().getUserName());
        userVo.setUserNo(tPurpose.gettUser().getUserNo());
        TPurpose parent = tPurpose.getParent();
        if (parent != null) {
            userVo.setHasParent(true);
        } else {
            userVo.setHasParent(false);
        }
    }

    private void fillCondition(PurposeVo purposeVo, List<Condition> conList, String pId) {
        LoginUserInfo loginUserInfo = purposeVo.getLoginUserInfo();
        Long id = loginUserInfo.getId();
        conList.add(new Condition("tUser.id", id, Condition.EQUAL_TO));
        if (StringUtils.isEmpty(pId)) {
            conList.add(new Condition("parent", null, Condition.NULL));
        } else {
            conList.add(new Condition("parent.id", Long.parseLong(pId), Condition.EQUAL_TO));
        }
    }

    @Override
    public void save(PurposeVo purposeVo) throws Exception {
        TPurpose tPayUser = new TPurpose();
        this.copyVoToEntity(tPayUser, purposeVo);
        purposeDao.save(tPayUser);
    }

    private void copyVoToEntity(TPurpose tPurpose, PurposeVo purposeVo) throws Exception {
        BeanUtils.copyProperties(purposeVo, tPurpose);
        LoginUserInfo loginUserInfo = purposeVo.getLoginUserInfo();
        Long id = loginUserInfo.getId();
        TUser tUser = purposeDao.find(id, TUser.class);
        tPurpose.settUser(tUser);
        String pId = purposeVo.getpId();
        if (!StringUtils.isEmpty(pId)) {
            TPurpose parent = purposeDao.find(Long.parseLong(pId), TPurpose.class);
            tPurpose.setParent(parent);
        }
        tPurpose.setVisible(true);
        Integer purposeType = purposeVo.getPurposeType();
        tPurpose.setPurposeType(purposeType);
    }

    @Override
    public PurposeVo get(Long id) throws Exception {
        TPurpose tPayUser = purposeDao.find(id, TPurpose.class);
        PurposeVo purposeVo = new PurposeVo();
        this.copyEntityToVo(tPayUser, purposeVo);
        return purposeVo;
    }

    @Override
    public void update(PurposeVo purposeVo) throws Exception {
        Long id = purposeVo.getId();
        TPurpose tPurpose = purposeDao.find(id, TPurpose.class);
        Date createDate = tPurpose.getCreateDate();
        String createUser = tPurpose.getCreateUser();
        this.copyVoToEntity(tPurpose, purposeVo);
        tPurpose.setCreateDate(createDate);
        tPurpose.setCreateUser(createUser);
        purposeDao.update(tPurpose);
    }

    @Override
    public void delete(Long id) throws Exception {
        TPurpose tPayUser = purposeDao.find(id, TPurpose.class);
        purposeDao.delete(tPayUser);
    }

    @Override
    public void changeStatus(Long id, String status) throws Exception {
        TPurpose tPurpose = purposeDao.find(id, TPurpose.class);
        if (status.equals("-1")) {
            tPurpose.setVisible(false);
        } else {
            tPurpose.setVisible(true);
        }
        purposeDao.update(tPurpose);
    }
}
