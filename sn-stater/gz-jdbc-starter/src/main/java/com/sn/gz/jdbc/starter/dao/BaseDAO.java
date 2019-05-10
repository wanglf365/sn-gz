/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: BaseDao
 * Author:   xiaoxueliang
 * Date:     2018/7/7 10:08
 * @since 1.0.0
 */
package com.sn.gz.jdbc.starter.dao;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.jdbc.starter.query.Filter;
import com.sn.gz.jdbc.starter.query.OrderBy;
import com.sn.gz.jdbc.starter.query.PageWrapper;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * baseDao
 *
 * @author xiaoxueliang
 * 2018/7/7 10:08
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class BaseDAO<T, ID extends Serializable> {
    /**
     * 获取实现类的mapper
     *
     * @return com.baomidou.mybatisplus.mapper.BaseMapper&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/9 12:08
     * @since 1.0.0
     */
    public abstract BaseMapper<T> getMapper();

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    public BaseDAO() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
    }

    /**
     * 保存
     *
     * @param entity 实体
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/7 17:21
     * @since 1.0.0
     */
    public Integer save(T entity) {
        return getMapper().insert(entity);
    }

    /**
     * 按主键删除
     *
     * @param id 主键
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/7 17:21
     * @since 1.0.0
     */
    public Integer deleteById(ID id) {
        return getMapper().deleteById(id);
    }

    /**
     * 按条件删除<br>
     * 注意：<br>
     * 1、当filterList为null时执行全表删除，<br>
     * 2、filterList不为null时必须存在有效的过滤条件，否则将抛出异常
     *
     * @param filterList 过滤条件，<br>example：Filter.build().add(Filter.eq("property", "value")).build();
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public Integer delete(List<Filter> filterList) {
        return getMapper().delete(processFilterList(filterList, true));
    }

    /**
     * 按条件删除
     *
     * @param filter 过滤条件
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public Integer delete(Filter filter) {
        List<Filter> filterList = new ArrayList<>();
        filterList.add(filter);
        return delete(filterList);
    }

    /**
     * 按id批量删除
     *
     * @param idList id集合
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public Integer deleteBatchByIds(List<ID> idList) {
        return getMapper().deleteBatchIds(idList);
    }

    /**
     * 按id更新
     *
     * @param entity 实体
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public Integer updateById(T entity) {
        return getMapper().updateById(entity);
    }

    /**
     * 根据id更新所有列
     *
     * @param entity 实体对象
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public Integer updateAllColumnById(T entity) {
        return getMapper().updateAllColumnById(entity);
    }

    /**
     * 按条件更新
     * 注意：<br>
     * 1、当filterList为null时执行全表删除，<br>
     * 2、filterList不为null时必须存在有效的过滤条件，否则将抛出异常
     *
     * @param entity     要更新的信息
     * @param filterList 过滤条件，<br>example：Filter.build().add(Filter.eq("property", "value")).build();
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public Integer update(T entity, List<Filter> filterList) {
        return getMapper().update(entity, processFilterList(filterList, true));
    }

    /**
     * 按id获取
     *
     * @param id 主键
     * @return T
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public T selectById(ID id) {
        return getMapper().selectById(id);
    }

    /**
     * 按id获取，使用悲观锁
     *
     * @param id 主键
     * @return T
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public T selectByIdForUpdate(ID id) {
        List<Filter> filterList = Filter.build()
                .add(Filter.eq("id", id))
                .add(Filter.forUpdate())
                .build();
        List<T> list = getMapper().selectList(processFilterList(filterList));
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * 按id批量获取
     *
     * @param idList 主键集合
     * @return java.util.List&lt;T&gt; 返回集合
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public List<T> selectBatchByIds(List<ID> idList) {
        return getMapper().selectBatchIds(idList);
    }

    /**
     * 按id批量获取，使用悲观锁
     *
     * @param idList 主键集合
     * @return java.util.List&lt;T&gt; 返回集合
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public List<T> selectBatchByIdsForUpdate(List<ID> idList) {
        List<Filter> filterList = Filter.build()
                .add(Filter.in("id", idList))
                .add(Filter.forUpdate())
                .build();
        return getMapper().selectList(processFilterList(filterList));
    }

    /**
     * 查询单个
     *
     * @param entity 查询条件
     * @return T
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public T selectOne(T entity) {
        return getMapper().selectOne(entity);
    }

    /**
     * 查询单个，如果找到多个结果将抛出异常
     *
     * @param filterList 过滤条件，<br>example：Filter.build().add(Filter.eq("property", "value")).build();
     * @return T
     * @author xiaoxueliang
     * 2018/7/7 17:22
     * @since 1.0.0
     */
    public T selectOne(List<Filter> filterList) {
        List<T> list = selectList(filterList);
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            if (list.size() > 1) {
                throw new RuntimeException("get more than one result");
            } else {
                return list.get(0);
            }
        }
    }

    /**
     * 查询总条数（根据过滤条件）
     *
     * @param filter 过滤条件，<br>example：Filter.eq("property", "value");
     * @return java.lang.Integer
     * @author Enma.ai
     * 2018-8-21
     */
    public Integer selectCount(Filter filter) {
        List<Filter> list = new ArrayList<>();
        list.add(filter);
        return getMapper().selectCount(processFilterList(list));
    }

    /**
     * 查询总条数（根据过滤条件）
     *
     * @param filterList 过滤条件，<br>example：Filter.build().add(Filter.eq("property", "value")).build();
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/7 17:23
     * @since 1.0.0
     */
    public Integer selectCount(List<Filter> filterList) {
        return getMapper().selectCount(processFilterList(filterList));
    }

    /**
     * 查询列表
     *
     * @return java.util.List&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 17:23
     * @since 1.0.0
     */
    public List<T> selectAll() {
        return getMapper().selectList(null);
    }

    /**
     * 查询列表（根据过滤条件）
     *
     * @param filter 过滤条件
     * @return java.util.List&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 17:23
     * @since 1.0.0
     */
    public List<T> selectList(Filter filter) {
        List<Filter> list = new ArrayList<>();
        list.add(filter);
        return getMapper().selectList(processFilterList(list));
    }

    /**
     * 查询列表（根据过滤条件）
     *
     * @param filterList 过滤条件，<br>example：Filter.build().add(Filter.eq("property", "value")).build();
     * @return java.util.List&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 17:23
     * @since 1.0.0
     */
    public List<T> selectList(List<Filter> filterList) {
        return getMapper().selectList(processFilterList(filterList));
    }


    /**
     * 查询列表（根据过滤条件）
     *
     * @param filterList 过滤条件，<br>example：Filter.build().add(Filter.eq("property", "value")).build();
     * @param count      最大条数
     * @return java.util.List&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 17:23
     * @since 1.0.0
     */
    public List<T> selectList(List<Filter> filterList, Integer count) {
        List<Filter> list = new ArrayList<>();
        if (filterList != null) {
            list.addAll(filterList);
        }
        if (count != null) {
            list.add(Filter.count(count));
        }
        return getMapper().selectList(processFilterList(list));
    }

    /**
     * 查询列表（根据过滤条件）
     *
     * @param filterList  过滤条件，<br>example：Filter.build().add(Filter.eq("property", "value")).build();
     * @param orderByList 排序，<br>example：OrderBy.build().add(OrderBy.asc("property")).build();
     * @return java.util.List&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 17:23
     * @since 1.0.0
     */
    public List<T> selectList(List<Filter> filterList, List<OrderBy> orderByList) {
        return getMapper().selectList(processFilterListAndOrderByList(filterList, orderByList));
    }

    /**
     * 分页查询
     *
     * @param pageWrapper 分页对象，<br>example：new PageWrapper<T>().add(Filter.eq("property", "value")).add(OrderBy.asc("property"));
     * @return com.dianji.pangu.jdbc.query.PageWrapper&lt;T&gt; 分页结果
     * @author xiaoxueliang
     * 2018/7/7 17:23
     * @since 1.0.0
     */
    public PageWrapper<T> selectPage(PageWrapper<T> pageWrapper) {
        Page<T> mybatisPlusPage = getMyBatisPlusPage(pageWrapper);
        List<T> list = getMapper().selectPage(mybatisPlusPage, getEntityClassWrapper(pageWrapper));
        pageWrapper.setData(list);
        pageWrapper.setTotal(mybatisPlusPage.getTotal());
        return pageWrapper;
    }

    /**
     * 获取mybatisPlus分页对象
     *
     * @param pageWrapper mybatisPlus分页对象
     * @return com.baomidou.mybatisplus.plugins.PageWrapper&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 17:23
     * @since 1.0.0
     */
    protected Page<T> getMyBatisPlusPage(PageWrapper<T> pageWrapper) {
        Page<T> plusPage = new Page<>();
        plusPage.setCurrent(pageWrapper.getCurrent());
        plusPage.setSize(pageWrapper.getSize());
        return plusPage;
    }

    /**
     * 获取mybatisPlus分页对象
     *
     * @param pageWrapper 用户自定义model分页对象
     * @return com.baomidou.mybatisplus.plugins.PageWrapper&lt;T&gt;
     * @author Enma.ai
     * 2018-8-10
     */
    protected <V> Page<V> getUserMyBatisPlusPage(PageWrapper<V> pageWrapper) {
        Page<V> plusPage = new Page<>();
        plusPage.setCurrent(pageWrapper.getCurrent());
        plusPage.setSize(pageWrapper.getSize());
        return plusPage;
    }

    /**
     * 获取mybatisPlus条件过滤对象
     *
     * @param pageWrapper 分页对象
     * @return com.dianji.pangu.jdbc.mapper.EntityClassWrapper&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 17:23
     * @since 1.0.0
     */
    protected EntityClassWrapper<T> getEntityClassWrapper(PageWrapper<T> pageWrapper) {

        if (pageWrapper == null) {
            return null;
        }

        EntityClassWrapper<T> entityClassWrapper = new EntityClassWrapper<>(clazz);

        processFilterList(entityClassWrapper, pageWrapper.getFilters());

        processOrderByList(entityClassWrapper, pageWrapper.getOrderByList());

        return entityClassWrapper;
    }

    /**
     * 获取包装器
     *
     * @param filterList 过滤集，为null或为空返回null
     * @return com.dianji.pangu.jdbc.mapper.EntityClassWrapper&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 20:03
     * @since 1.0.0
     */
    protected EntityClassWrapper<T> processFilterList(List<Filter> filterList) {
        if (filterList == null || filterList.isEmpty()) {
            return null;
        }
        EntityClassWrapper<T> entityClassWrapper = new EntityClassWrapper<>(clazz);
        processFilterList(entityClassWrapper, filterList, false);
        return entityClassWrapper;
    }

    /**
     * 获取包装器
     *
     * @param filterList 过滤集，为null或为空返回null
     * @param safetyCheck        安全检查（如：删除，更新操作必须存在有效的过滤条件）
     * @return com.dianji.pangu.jdbc.mapper.EntityClassWrapper&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 20:03
     * @since 1.0.0
     */
    protected EntityClassWrapper<T> processFilterList(List<Filter> filterList, boolean safetyCheck) {
        EntityClassWrapper<T> entityClassWrapper = new EntityClassWrapper<>(clazz);
        processFilterList(entityClassWrapper, filterList, safetyCheck);
        return entityClassWrapper;
    }

    /**
     * 获取包装器
     *
     * @param filterList 过滤集，为null或为空返回null
     * @return com.dianji.pangu.jdbc.mapper.EntityClassWrapper&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 20:03
     * @since 1.0.0
     */
    protected EntityClassWrapper<T> processFilterListAndOrderByList(List<Filter> filterList, List<OrderBy> orderByList) {
        EntityClassWrapper<T> entityClassWrapper = new EntityClassWrapper<>(clazz);
        processFilterList(entityClassWrapper, filterList);
        processOrderByList(entityClassWrapper, orderByList);
        return entityClassWrapper;
    }

    /**
     * 处理过滤集
     *
     * @param entityClassWrapper 条件包装器
     * @param filterList         过滤集
     * @author xiaoxueliang
     * 2018/7/7 19:53
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    protected void processFilterList(EntityClassWrapper<T> entityClassWrapper, List<Filter> filterList) {
       processFilterList(entityClassWrapper, filterList, false);
    }

    /**
     * 处理过滤集
     *
     * @param entityClassWrapper 条件包装器
     * @param filterList         过滤集
     * @param safetyCheck        安全检查（如：删除，更新操作必须存在有效的过滤条件）
     * @author xiaoxueliang
     * 2018/7/7 19:53
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    protected void processFilterList(EntityClassWrapper<T> entityClassWrapper, List<Filter> filterList, boolean safetyCheck) {

        if (filterList != null && filterList.size() > 0) {
            boolean lock = false;
            Integer limitCount = null;
            for (Filter localFilter : filterList) {
                if (localFilter == null) {
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.andNew) {
                    entityClassWrapper.andNew();
                    continue;
                }

                if (localFilter.getFilterType() == Filter.FilterType.or) {
                    entityClassWrapper.or();
                    continue;
                }

                if (localFilter.getFilterType() == Filter.FilterType.orNew) {
                    entityClassWrapper.orNew();
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.forUpdate) {
                    lock = true;
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.count) {
                    if (localFilter.getCount() == null || localFilter.getCount() < 0) {
                        continue;
                    }
                    limitCount = localFilter.getCount();
                }

                String property = localFilter.getProperty();
                Object value = localFilter.getValue();
                Object value2 = localFilter.getValue2();

                if (StringUtils.isBlank(property)) {
                    continue;
                }

                if (localFilter.getFilterType() == Filter.FilterType.isNull) {
                    entityClassWrapper.isNull(property);
                    continue;
                }

                if (localFilter.getFilterType() == Filter.FilterType.isNotNull) {
                    entityClassWrapper.isNotNull(property);
                    continue;
                }

                if (value == null) {
                    continue;
                }

                if (StringUtils.isBlank(value.toString())) {
                    continue;
                }

                if (localFilter.getFilterType() == Filter.FilterType.eq) {
                    entityClassWrapper.eq(property, value);
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.ne) {
                    entityClassWrapper.ne(property, value);
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.gt) {
                    entityClassWrapper.gt(property, value);
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.lt) {
                    entityClassWrapper.lt(property, value);
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.ge) {
                    entityClassWrapper.ge(property, value);
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.le) {
                    entityClassWrapper.le(property, value);
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.like) {
                    Filter.SqlLike sqlLike = localFilter.getSqlLike();
                    if (sqlLike == null) {
                        continue;
                    }
                    entityClassWrapper.like(property, value.toString(), SqlLike.valueOf(localFilter.getSqlLike().name()));
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.in) {
                    List<?> inList = (List<?>) value;
                    if (inList.isEmpty()) {
                        entityClassWrapper.where(" 1 != 1");
                        continue;
                    }
                    entityClassWrapper.in(property, inList);
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.notIn) {
                    List<Object> inList = (List<Object>) value;
                    if (inList.isEmpty()) {
                        continue;
                    }
                    entityClassWrapper.notIn(property, inList);
                    continue;
                }
                if (localFilter.getFilterType() == Filter.FilterType.between) {
                    if (value2 == null) {
                        continue;
                    }
                    if (StringUtils.isBlank(value2.toString())) {
                        continue;
                    }
                    entityClassWrapper.between(property, value, value2);
                }
            }
            StringBuilder sb = new StringBuilder();
            if (limitCount != null) {
                sb.append(" limit ");
                sb.append(limitCount);
            }
            if (lock) {
                sb.append(" for update");
            }
            if (sb.length() > 0) {
                entityClassWrapper.last(sb.toString());
            }

        }

        if (safetyCheck) {
            if (filterList != null && entityClassWrapper.isEmptyOfWhere()) {
                throw new RuntimeException("not exist valid filter, current operation may delete or update full table");
            }
        }

    }

    /**
     * 获取包装器
     *
     * @param orderByList 排序集
     * @return com.dianji.pangu.jdbc.mapper.EntityClassWrapper&lt;T&gt;
     * @author xiaoxueliang
     * 2018/7/7 20:07
     * @since 1.0.0
     */
    protected EntityClassWrapper<T> processOrderByList(List<OrderBy> orderByList) {
        if (orderByList == null || orderByList.isEmpty()) {
            return null;
        }
        EntityClassWrapper<T> entityClassWrapper = new EntityClassWrapper<>(clazz);
        processOrderByList(entityClassWrapper, orderByList);
        return entityClassWrapper;
    }

    /**
     * 处理排序
     *
     * @param entityClassWrapper 条件包装器
     * @param orderByList        排序集
     * @author xiaoxueliang
     * 2018/7/7 19:52
     * @since 1.0.0
     */
    protected void processOrderByList(EntityClassWrapper<T> entityClassWrapper, List<OrderBy> orderByList) {
        if (orderByList != null && orderByList.size() > 0) {
            for (OrderBy orderBy : orderByList) {
                if (orderBy == null) {
                    continue;
                }
                String property = orderBy.getProperty();
                OrderBy.OrderType orderType = orderBy.getOrderType();

                if (StringUtils.isBlank(property)) {
                    continue;
                }
                entityClassWrapper.orderBy(property, orderType == OrderBy.OrderType.ASC);
            }
        }
    }
}