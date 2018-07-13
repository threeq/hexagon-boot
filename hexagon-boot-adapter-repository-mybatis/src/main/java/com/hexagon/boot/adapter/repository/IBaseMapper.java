package com.hexagon.boot.adapter.repository;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 基础类 MAPPER
 *
 * @author three
 */
public interface IBaseMapper<M> {
    /**
     * 获取 序列号
     *
     * @return
     * @author three
     */
    Long getIdValue();

    /**
     * 插入记录
     *
     * @param obj
     * @return
     * @author three
     */
    int insert(M obj);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertBatch(List<M> list);

    /**
     * 插入记录(有效字段,即非空字段)
     *
     * @param obj
     * @return
     * @author three
     */
    int insertSelective(M obj);

    /**
     * 物理删除记录
     *
     * @param seq
     * @return
     * @author three
     */
    <K>int deleteByPrimaryKey(K seq);

    /**
     * 更新记录
     *
     * @param obj
     * @return
     * @author three
     */
    int updateByPrimaryKey(M obj);

    /**
     * 更新记录(有效字段,即非空字段)
     *
     * @param obj
     * @return
     * @author three
     */
    int updateByPrimaryKeySelective(M obj);

    /**
     * 根据主键 返回记录
     *
     * @param seq
     * @return
     * @author three
     */
    M selectByPrimaryKey(Object seq);

    /**
     * 根据 条件返回记录
     *
     * @param params
     * @return
     * @author three
     */
    M selectByParams(@Param(value = "params") Map<String, Object> params);

    /**
     * 查询 符合条件的记录总数
     *
     * @param params
     * @return
     * @author three
     */
    int selectCountByParams(@Param(value = "params") Map<String, Object> params);

    /**
     * 分页查询 记录，分页条件为null，返回所有
     *
     * @param params     查询条件
     * @param pageOffset 开始游标
     * @param pageSize   每页显示的数量z
     * @param orderParam 排序参数
     * @return
     * @author three
     */
    List<M> selectListByParams(@Param(value = "params") Map<String, Object> params, @Param(value = "pageOffset") Integer pageOffset, @Param(value = "pageSize") Integer pageSize, @Param(value = "orderParam") String orderParam);
}
