package com.pjm.familyAccountWx.dao;

import com.pjm.familyAccountWx.common.BaseDao;
import com.pjm.familyAccountWx.model.TUser;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by PanJM on 2016/11/19.
 */
@Repository
public class UserDao extends BaseDao {

    public TUser queryByNameAndPwd(String userName, String passWord) {
        Query query = em.createQuery("select t from TUser t where t.userName =:userName and t.passWord =:passWord");
        query.setParameter("userName", userName);
        query.setParameter("passWord", passWord);
        List<TUser> resultList = query.getResultList();
        TUser tUser = null;
        if (resultList != null && resultList.size() > 0) {
            tUser = resultList.get(0);
        }
        return tUser;
    }
}
