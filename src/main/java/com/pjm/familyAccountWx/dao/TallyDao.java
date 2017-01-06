package com.pjm.familyAccountWx.dao;

import com.pjm.familyAccountWx.common.dao.BaseDao;
import com.pjm.familyAccountWx.common.util.CollectionsUtil;
import com.pjm.familyAccountWx.common.vo.Condition;
import com.pjm.familyAccountWx.common.vo.PageModel;
import com.pjm.familyAccountWx.common.vo.QueryResult;
import com.pjm.familyAccountWx.model.TPayUser;
import com.pjm.familyAccountWx.model.TTally;
import com.pjm.familyAccountWx.vo.TallyVo;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by PanJM on 2016/11/22.
 */
@Repository
public class TallyDao extends BaseDao {

    public QueryResult<TTally> getTallyList(TallyVo tallyParam, PageModel ph, Long userId) throws Exception {

        List<Condition> conditions = CollectionsUtil.newArrayList();

        Long accountId = tallyParam.getAccountId();
        Integer purposeType = tallyParam.getPurposeType();
        String payUserId = tallyParam.getPayUserId();
        String remark = tallyParam.getRemark();

        if (!StringUtils.isEmpty(accountId)) {
            conditions.add(new Condition("tAccount.id", accountId, Condition.EQUAL_TO));
        }
        if (!StringUtils.isEmpty(purposeType)) {
            conditions.add(new Condition("tPurpose.purposeType", purposeType, Condition.EQUAL_TO));
        }
        if (!StringUtils.isEmpty(payUserId)) {
            TPayUser tPayUser = this.find(Long.parseLong(payUserId), TPayUser.class);
            Set<TTally> tTallySet = tPayUser.gettTallySet();
            List<Long> longList = new ArrayList<Long>();
            if (tTallySet != null && tTallySet.size() > 0) {
                if (!StringUtils.isEmpty(purposeType) && purposeType == -1) {
                    for (TTally tTally : tTallySet) {
                        Long id = tTally.getId();
                        longList.add(id);
                    }
                    conditions.add(new Condition("id", longList, Condition.IN));
                }
            } else {
                return new QueryResult<TTally>();
            }
        }
        if (!StringUtils.isEmpty(remark)) {
            conditions.add(new Condition("remark", "%" + remark + "%", Condition.LIKE));
        }
        String tallyNo = tallyParam.getTallyNo();
        if (!StringUtils.isEmpty(tallyNo)) {
            conditions.add(new Condition("tallyNo", "%" + tallyNo + "%", Condition.LIKE));
        }
        Boolean visible = tallyParam.getVisible();
        Boolean all = tallyParam.getStatus();
        if (all != null && all) {
            if (!StringUtils.isEmpty(visible)) {
                conditions.add(new Condition("visible", visible, Condition.EQUAL_TO));
            }
        }
        conditions.add(new Condition("tUser.id", userId, Condition.EQUAL_TO));

        ph.setSort("payDate,createDate");
        ph.setOrder(PageModel.desc + "," + PageModel.desc);

        QueryResult<TTally> pageResult = this.getPageResult(TTally.class, conditions, ph);
        return pageResult;
    }
}
