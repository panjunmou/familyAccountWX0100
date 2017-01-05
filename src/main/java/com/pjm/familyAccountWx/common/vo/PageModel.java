package com.pjm.familyAccountWx.common.vo;

import com.pjm.familyAccountWx.common.util.CollectionsUtil;

import java.io.Serializable;
import java.util.List;

/**
 * @author PanJM
 * @since 2016.11.19
 */
public class PageModel implements Serializable {

    private static final long serialVersionUID = -3542668843268550832L;

    public static final String asc = "ASC";

    public static final String desc = "DESC";

    public static final int DEFAULT_SIZE = 100;

    private boolean success = true;

    private String value;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页
     */
    private int page;

    /**
     * 每页显示记录数
     */

    private int pageSize;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * asc/desc
     */
    private String order;

    /**
     * 查询结果集
     */
    private List<?> rows;

    private List<?> footer;

    public PageModel() {
    }

    public PageModel(String sort, String order) {
        this.sort = sort;
        this.order = order;
    }

    public PageModel(int page, int pageSize, String sort, String order) {
        super();
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
        this.order = order;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<?> getRows() {
        if (rows == null) {
            rows = CollectionsUtil.newArrayList();
        }
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public int getStart() {
        if (page == 0) {
            page = 1;
        }
        return (page - 1) * pageSize;
    }

    public void orderBy(String sortField, String orderType) {
        this.sort = sortField;
        this.order = orderType;
    }

    public List<?> getFooter() {
        return footer;
    }

    public void setFooter(List<?> footer) {
        this.footer = footer;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
