package com.pjm.familyAccountWx.dao;

import com.pjm.familyAccountWx.common.BaseDao;
import com.pjm.familyAccountWx.model.TPayUser;
import com.pjm.familyAccountWx.model.TUser;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by PanJM on 2016/11/22.
 */
@Repository
public class PayUserDao extends BaseDao {

    public List<TPayUser> getPayUserByUserId(Long id) {
        Query query = em.createQuery("select t from TPayUser t where t.tUser.id =:userId order by t.seq asc ");
        query.setParameter("userId", id);
        List resultList = query.getResultList();
        return resultList;
    }
}
