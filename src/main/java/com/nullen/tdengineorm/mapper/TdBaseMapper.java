package com.nullen.tdengineorm.mapper;

import com.nullen.tdengineorm.dto.Page;
import com.nullen.tdengineorm.entity.TdBaseEntity;
import com.nullen.tdengineorm.strategy.DynamicNameStrategy;
import com.nullen.tdengineorm.wrapper.AbstractTdWrapper;
import com.nullen.tdengineorm.wrapper.TdQueryWrapper;

import java.util.List;

/**
 * TDengine基础Mapper接口
 *
 * @author Nullen
 */
public interface TdBaseMapper<T extends TdBaseEntity> {

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return 影响行数
     */
    int insert(T entity);

    int insert(T entity, DynamicNameStrategy dynamicNameStrategy);

    /**
     * 批量插入记录
     *
     * @param entityList 实体对象集合
     * @return 影响行数数组
     */
    int[] insertBatch(List<T> entityList);

    /**
     * 插入数据并自动创建子表(如果子表不存在)
     * 相对直接入库更消耗性能, 不推荐频繁使用
     *
     * @param entity 实体对象
     * @return 影响行数
     */
    int insertUsing(T entity);

    /**
     * 批量插入数据并自动创建子表(如果子表不存在)
     * 相对直接入库更消耗性能, 不推荐频繁使用
     *
     * @param entityList 实体对象集合
     */
    void batchInsertUsing(List<T> entityList);

    /**
     * 根据时间戳删除记录
     *
     * @param ts 时间戳
     * @return 影响行数
     */
    int deleteByTs(Long ts);

    /**
     * 批量根据时间戳删除记录
     *
     * @param tsList 时间戳列表
     * @return 影响行数
     */
    int batchDeleteByTs(List<Long> tsList);

    /**
     * 根据条件删除记录
     *
     * @param wrapper 条件包装器
     * @return 影响行数
     */
    int delete(AbstractTdWrapper<T> wrapper);

    /**
     * 根据时间戳查询记录
     *
     * @param ts 时间戳
     * @return 实体对象
     */
    T selectByTs(Long ts);

    /**
     * 查询最新的一条记录（按ts降序）
     *
     * @return 实体对象
     */
    T selectLastOne();

    /**
     * 查询所有记录
     *
     * @return 实体对象集合
     */
    List<T> selectAll();

    /**
     * 根据条件查询记录
     *
     * @param wrapper 条件包装器
     * @return 实体对象集合
     */
    List<T> selectList(AbstractTdWrapper<T> wrapper);

    /**
     * 根据条件查询单条记录
     *
     * @param wrapper 条件包装器
     * @return 实体对象
     */
    T selectOne(AbstractTdWrapper<T> wrapper);

    /**
     * 根据条件查询记录数
     *
     * @param wrapper 条件包装器
     * @return 记录数
     */
    long selectCount(AbstractTdWrapper<T> wrapper);

    /**
     * 分页查询
     *
     * @param pageNo   页码
     * @param pageSize 每页大小
     * @param wrapper  条件包装器
     * @return 分页结果
     */
    Page<T> selectPage(long pageNo, long pageSize, TdQueryWrapper<T> wrapper);

    /**
     * 创建超级表
     *
     * @return 影响行数
     */
    int createStableTable();
} 