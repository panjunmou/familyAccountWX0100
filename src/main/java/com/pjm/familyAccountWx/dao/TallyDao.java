package com.pjm.familyAccountWx.dao;

import com.pjm.familyAccountWx.common.*;
import com.pjm.familyAccountWx.model.TPurpose;
import com.pjm.familyAccountWx.model.TTally;
import com.pjm.familyAccountWx.vo.TallyParam;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by PanJM on 2016/11/22.
 */
@Repository
public class TallyDao extends BaseDao {

    public QueryResult<TTally> getTallyList(TallyParam tallyParam, PageModel ph) throws Exception {


        List<Condition> conditions = CollectionsUtil.newArrayList();

        PageModel pageModel = null;
        if (ph != null && ph.getPageSize() > 0) {
            pageModel = new PageModel();
            pageModel.setPage(ph.getPage());
            pageModel.setPageSize(ph.getPageSize());
        }
        QueryResult<TTally> pageResult = this.getPageResult(TTally.class, conditions, pageModel);
        return pageResult;
    }
}
