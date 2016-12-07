package com.pjm.familyAccountWx.dao;

import com.pjm.familyAccountWx.common.dao.BaseDao;
import com.pjm.familyAccountWx.model.TAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by PanJM on 2016/11/22.
 */
@Repository
public class AccountDao extends BaseDao {

    public List<TAccount> getAccountListByUserId(Long id) {
        Query query = em.createQuery("select t from TAccount t where t.tUser.id =:userId");
        query.setParameter("userId", id);
        List resultList = query.getResultList();
        return resultList;
    }
}
