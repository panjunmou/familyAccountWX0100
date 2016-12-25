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
        StringBuffer sqlString = new StringBuffer("select t from TPurpose t where t.tUser.userName =:userName and t.purposeType =:purposeType and t.visible=true");
        Query query = em.createQuery(sqlString.toString());
        query.setParameter("userName", userName);
        query.setParameter("purposeType", purposeType);
        List resultList = query.getResultList();
        return resultList;
    }

    public List<TPurpose> queryPurposeByPId(String userName, Integer purposeType, Integer parentId) {
        StringBuffer sqlString = new StringBuffer("select t from TPurpose t where t.tUser.userName =:userName and t.purposeType =:purposeType");
        if (parentId != null) {
            sqlString.append(" and t.parent.id=:parentId");
        } else {
            sqlString.append(" and t.parent is null");
        }
        Query query = em.createQuery(sqlString.toString());
        query.setParameter("userName", userName);
        query.setParameter("purposeType", purposeType);
        if (parentId != null) {
            query.setParameter("parentId", parentId);
        }
        List resultList = query.getResultList();
        return resultList;
    }
}
