package com.hexagon.boot.domain;

import java.util.List;
import java.util.Map;

/**
 * 存储库基础操作定义
 *
 * todo 分页查询
 * todo 参数查询
 *
 * @param <E>
 */
public interface BaseRepository<E extends BaseEntity> {

    /**
     * 加载数据
     *
     * @param id
     * @return
     */
    E findById(Long id);

    /**
     * todo 查询参数
     * @return
     * @param params
     */
    E findByParam(Map<String, Object> params);

    /**
     * todo 参数查询
     * @return
     */
    List<E> findAll();

    /**
     * todo 参数查询
     * @return
     */
    long count();

    /**
     * todo 参数查询
     * todo 分页查询
     * @return
     * @param params
     * @param pageNum
     * @param pageSize
     * @param order
     */
    List<E> findPage(Map<String, Object> params, Integer pageNum, Integer pageSize, String order);

    /**
     * todo 分页查询
     * @return
     */
    List<E> findAllByIds(List<Long> ids);

    boolean existsById(Long id);

    /**
     * 批量保存
     * @param items
     * @returns
     */
    List<E> insertAll(List<E> items);

    /**
     * 保存数据
     *
     * @param entity
     * @return
     */
    E insert(E entity);

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    E update(E entity);

    /**
     * 更新数据，null 也会存储到数据库
     *
     * @param e
     * @return
     */
    E write(E e);

    /**
     * 删除数据
     *
     * @param entity
     * @return
     */
    void delete(E entity);

    void deleteById(Long id);

    void deleteAll(List<E> deleteList);

    void deleteAll();
}
