package com.pjm.familyAccountWx.dao;

import com.pjm.familyAccountWx.common.dao.BaseDao;
import com.pjm.familyAccountWx.model.TPurpose;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by PanJM on 2016/11/22.
 */
@Repository
public class PurposeDao extends BaseDao {

    public List<TPurpose> queryPurpose(String userName, Integer purposeType) {
        StringBuffer sqlString = new StringBuffer("select t from TPurpose t where t.tUser.userName =:userName and t.purposeType =:purposeType");
        Query query = em.createQuery(sqlString.toString());
        query.setParameter("userName", userName);
        query.setParameter("purposeType", purposeType);
        List resultList = query.getResultList();
        return resultList;
    }
}
