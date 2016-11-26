package com.pjm.familyAccountWx.service.impl;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.dao.PurposeDao;
import com.pjm.familyAccountWx.model.TPurpose;
import com.pjm.familyAccountWx.service.PurposeService;
import com.pjm.familyAccountWx.vo.PurposePicker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            for (Long purposeId : purposePickerMap.keySet()) {
                PurposePicker purposePicker = purposePickerMap.get(purposeId);
                purposePickerList.add(purposePicker);
            }
        }
        String jsonString = JSON.toJSONString(purposePickerList);
        return jsonString;
    }

    private void copyPurposeToPicker(TPurpose tPurpose, PurposePicker purposePicker) {
        purposePicker.setId(tPurpose.getId());
        purposePicker.setName(tPurpose.getName());
        TPurpose parent = tPurpose.getParent();
        if (parent == null) {
            purposePicker.setType("0");
        }
    }
}
