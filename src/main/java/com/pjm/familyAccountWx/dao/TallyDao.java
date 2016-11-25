package com.pjm.familyAccountWx.dao;

import com.pjm.familyAccountWx.common.BaseDao;
import com.pjm.familyAccountWx.model.TPurpose;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by PanJM on 2016/11/22.
 */
@Repository
public class TallyDao extends BaseDao {

    public List<TPurpose> queryPurpose(String userName) {
        Query query = em.createQuery("select t from TPurpose t where t.tUser.userName =:userName");
        query.setParameter("userName", userName);
        List resultList = query.getResultList();
        return resultList;
    }
}
