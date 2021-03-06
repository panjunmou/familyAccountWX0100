package com.pjm.familyAccountWx.common.dao;

import com.alibaba.fastjson.JSON;
import com.pjm.familyAccountWx.common.constants.DBConstants;
import com.pjm.familyAccountWx.common.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;


/**
 * DAO-基类
 *
 * @author AllenPang
 */
@Repository
public class BaseDao {

    private Logger logger = LoggerFactory.getLogger(BaseDao.class);

    public Logger getLogger() {
        return logger;
    }

    public static final int BATCH_SIZE = 500;

    @PersistenceContext
    protected EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Save object
     *
     * @param t
     * @return
     */
    public <T extends IdEntity> T save(T t) throws Exception {
        t.setCreateDate(new Date());
        em.persist(t);
        return t;
    }

    /***
     * 批量保存
     *
     * @param lists
     */
    public <T extends IdEntity> void save(List<T> lists) throws Exception {
        int batchCount = 0;
        for (T t : lists) {
            batchCount++;
            t.setCreateDate(new Date());
            em.persist(t);
            if (batchCount % BATCH_SIZE == 0) {
                em.flush();
                em.clear();
                batchCount = 0;
            }
        }
    }

    /**
     * Update object
     *
     * @param t
     * @return
     */
    public <T extends IdEntity> T update(T t) throws Exception {
        t.setUpdateDate(new Date());
        return em.merge(t);
    }

    /**
     * 批量更新
     *
     * @param lists
     */
    public <T extends IdEntity> void update(List<T> lists) throws Exception {
        int batchCount = 0;
        for (T t : lists) {
            batchCount++;
            t.setUpdateDate(new Date());
            em.merge(t);
            if (batchCount % BATCH_SIZE == 0) {
                em.flush();
                em.clear();
                batchCount = 0;
            }
        }
    }

    /**
     * Delete object
     *
     * @param t
     */
    public <T> void delete(T t) throws Exception {
        em.remove(em.merge(t));
    }

    /**
     * 批量删除
     *
     * @param lists
     */
    public <T> void delete(List<T> lists) throws Exception {
        int batchCount = 0;
        for (T t : lists) {
            em.remove(em.merge(t));
            batchCount++;
            if (batchCount % BATCH_SIZE == 0) {
                em.flush();
                em.clear();
                batchCount = 0;
            }
        }
    }

    /**
     * 参数名方式
     * <p>
     * 获取单个对象
     *
     * @param ql
     * @param parameters
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> T getSingleObject(String ql, Map<String, Object> parameters) throws Exception {
        try {
            Query query = em.createQuery(ql);
            Set<String> names = parameters.keySet();
            for (String name : names) {
                query.setParameter(name, parameters.get(name));
            }
            if (query.getResultList().size() == 0) {
                return null;
            } else {
                return (T) query.getResultList().get(0);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 获取List
     *
     * @param ql
     * @param parameters
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getObjects(String ql, Map<String, Object> parameters) throws Exception {
        try {
            Query query = em.createQuery(ql);
            Set<String> names = parameters.keySet();
            for (String name : names) {
                query.setParameter(name, parameters.get(name));
            }
            return (List<T>) query.getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    /**
     * 根据主键来获取对象
     *
     * @param primaryKey
     * @param clazz
     * @return
     */
    public <T> T find(Object primaryKey, Class<T> clazz) throws Exception {
        return em.find(clazz, primaryKey);
    }

    /**
     * 分页查询
     *
     * @param ql
     * @param clazz
     * @param page
     * @param parameters
     * @return
     */
    public <T> QueryResult<T> getPageResult(Class<T> clazz, String ql, PageModel page, Map<String, Object> parameters)
            throws Exception {
        QueryResult<T> result = new QueryResult<T>();
        long totalCount = getTotalCount(ql, parameters);
        List<T> lists = getObjects(createPageableQuery(ql, page), parameters);

        fillPageResult(page, result, totalCount, lists);
        return result;
    }

    private <T> void fillPageResult(PageModel page, QueryResult<T> result, long totalCount, List<T> lists)
            throws Exception {
        result.setReultList(lists);
        result.setTotalCount(totalCount);
        result.calcuatePageCount(page);
    }

    private Query createPageableQuery(String ql, PageModel page) throws Exception {
        Query query = em.createQuery(ql);
        if (page != null && page.getPage() > 0 && page.getPageSize() > 0) {
            query.setFirstResult(page.getStart());
            query.setMaxResults(page.getPageSize());
        }
        return query;
    }

    private String generateCountQuery(String ql) throws Exception {
        ql = ql.substring(ql.indexOf(DBConstants.FROM.trim()));
        ql = "select count(1) " + ql;
        if (ql.indexOf(DBConstants.ORDER_BY) != -1) {
            ql = ql.substring(0, ql.indexOf(DBConstants.ORDER_BY));
        }
        return ql;
    }

    private long getTotalCount(String ql, Map<String, Object> parameters) throws Exception {
        Query query = em.createQuery(generateCountQuery(ql));
        return (Long) getObjects(query, parameters).get(0);
    }

    /**
     * 参数名方式查询
     * <p>
     * 多条件查询，返回多条记录
     *
     * @param query
     * @param parameters 参数名， 参数值
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getObjects(Query query, Map<String, Object> parameters) throws Exception {
        Set<String> names = parameters.keySet();
        for (String name : names) {
            query.setParameter(name, parameters.get(name));
        }
        return query.getResultList();
    }

    /**
     * 多条件查询， 多列排序 仅支持 and 连接操作
     *
     * @param clazz
     * @param conditions
     * @param page
     * @return
     */
    public <T> QueryResult<T> getPageResult(Class<T> clazz, List<Condition> conditions, PageModel page)
            throws Exception {
        QueryBuilder builder = new QueryBuilder();
        builder.addConditions(conditions);
        builder.from(clazz);
        builder.where();
        builder.orderBy(page);

        logger.info(builder.toString());
        logger.info(JSON.toJSONString(conditions));
        return getPageResult(clazz, builder.toString(), page, builder.getParameters());
    }

    public <T> List<T> getResultList(Class<T> clazz, List<Condition> conditions, PageModel page) throws Exception {
        QueryBuilder builder = new QueryBuilder();
        builder.addConditions(conditions);
        builder.from(clazz);
        builder.where();
        builder.orderBy(page);

        return getResultList(clazz, builder.toString(), builder.getParameters());
    }

    private <T> List<T> getResultList(Class<T> clazz, String ql, Map<String, Object> parameters) throws Exception {
        List<T> lists = getObjects(createPageableQuery(ql, null), parameters);
        return lists;
    }

    /**
     * 多条件查询， 多列排序 仅支持 and 连接操作
     *
     * @param clazz
     * @param conditions
     * @return 根据查询条件返回单个对象
     */
    public <T> T getSingleResult(Class<T> clazz, List<Condition> conditions) throws Exception {
        QueryBuilder builder = new QueryBuilder();
        builder.addConditions(conditions);
        builder.from(clazz);
        builder.where();
        return getSingleObject(builder.toString(), builder.getParameters());
    }

    public String getSequenceNum(String type) throws Exception {
        String ql = "select seq_number from sys_sequence_number where seq_type =?1";
        Query query = em.createNativeQuery(ql);
        query.setParameter(1, type);
        List<?> result = query.getResultList();
        int id = 0;
        if (result != null) {
            Iterator<?> iterator = result.iterator();
            while (iterator.hasNext()) {
                id = ((BigInteger) iterator.next()).intValue();
            }
        }
        updateSequence(id, type);
        String seq = String.valueOf(id);
        for (int i = 0; i < 4 - seq.length(); i++) {
            seq = "0" + seq;
        }
        return seq;
    }

    private void updateSequence(int value, String type) throws Exception {
        String sql = "update sys_sequence_number set seq_number =?1 where seq_type =?2";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, value + 1);
        query.setParameter(2, type);

        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<T> clazz, String hql) throws Exception {
        Query q = em.createQuery(hql);
        return q.getResultList();
    }

    public String callBizSeqCode(String seqType) throws Exception {
        System.out.println("BaseDao.callBizSeqCode");
        String ql = "CALL getBizSeqCodePrd(?1)";
        Query query = em.createNativeQuery(ql);
        query.setParameter(1, seqType);
        List<?> result = query.getResultList();
        String seqCode = null;
        if (result != null) {
            Iterator<?> iterator = result.iterator();
            while (iterator.hasNext()) {
                seqCode = ((String) iterator.next());
            }
        }
        return seqCode;
    }

}
