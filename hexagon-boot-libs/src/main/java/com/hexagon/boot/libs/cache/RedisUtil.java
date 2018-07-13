package com.hexagon.boot.libs.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis 工具类
 *
 * @author three
 */
@Component
@ConditionalOnProperty(name = "spring.redis.host")
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    // HashMap操作
    @Resource
    private HashOperations<String, String, Object> hashOperations;
    // Object操作
    @Resource
    private ValueOperations<String, Object> valueOperations;
    // List操作
    @Resource
    private ListOperations<String, Object> listOperations;
    // set操作
    @Resource
    private SetOperations<String, Object> setOperations;
    // ZSet操作
    @Resource
    private ZSetOperations<String, Object> zSetOperations;

    //---------------------------------------------------------------------
    // redisTemplate
    //---------------------------------------------------------------------

    /**
     * 判断key是否存在
     * @param key
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 重命名key
     */
    public void renameKey(String key,String newKey){
        redisTemplate.rename(key,newKey);
    }

    /**
     * 删除key
     * @param key
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }

    /**
     * 判断指定key的hashKey是否存在
     * @param key
     * @param hashKey
     * @return
     */
    public boolean hasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 设置超时时间
     * @param key
     * @param timeout
     * @param unit
     */
    public void expire(String key, final long timeout, final TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取过期时间
     * @param key
     * @return 单位 秒
     */
    public long ttl(String key){
        return redisTemplate.getExpire(key);
    }

    /**
     * 获取指定pattern的key
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 删除多个key
     * @param keys
     */
    public void delete(Set<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 设置过期时间
     * @param key
     * @param expire
     */
    private void setExpire (String key,long expire){
        if (expire != -1) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    //---------------------------------------------------------------------
    // ValueOperations -> Redis String/Value 操作
    //---------------------------------------------------------------------

    /**
     * 设置key-value值
     * @param key
     * @param value
     * @param expire  单位 秒
     */
    public void addValue(String key, Object value,long expire){
        valueOperations.set(key, value);

        setExpire(key,expire);
    }

    /**
     * 设置key-value值,传入时间单位
     */
    public void addValue(String key, Object value,long expire, TimeUnit timeUnit){
        valueOperations.set(key, value, expire, timeUnit);

    }

    /**
     * 设置key-value值, 无过期时间
     */
    public void addValue(String key, Object value){
        valueOperations.set(key, value);
    }

    /**
     * 获取key的值
     *
     */
    public Object getValue(String key){
        return valueOperations.get(key);
    }

    //---------------------------------------------------------------------
    // HashOperations -> Redis Redis Hash 操作
    //---------------------------------------------------------------------

    /**
     * 向redis 中添加内容
     * @param key       保存key
     * @param hashKey   hashKey
     * @param data      保存对象 data
     * @param expire    过期时间    -1：表示不过期
     */
    public void addHashValue(String key,String hashKey, Object data, long expire) {
        hashOperations.put(key, hashKey, data);

        setExpire(key,expire);
    }

    /**
     * Hash 添加数据
     * @param key   key
     * @param map   data
     */
    public void addAllHashValue(String key, Map<String, Object> map, long expire) {
        hashOperations.putAll(key, map);

        setExpire(key,expire);
    }

    /**
     * 删除hash key
     * @param key       key
     * @param hashKey   hashKey
     */
    public long deleteHashValue(String key, String hashKey) {
        return hashOperations.delete(key, hashKey);
    }

    /**
     * 获取数据
     */
    public Object getHashValue(String key, String hashKey) {
        return hashOperations.get(key, hashKey);
    }

    /**
     * 批量获取数据
     */
    public List<Object> getHashAllValue(String key) {
        return hashOperations.values(key);
    }

    /**
     * 批量获取指定hashKey的数据
     */
    public List<Object> getHashMultiValue(String key, List<String> hashKeys) {
        return  hashOperations.multiGet(key, hashKeys);
    }

    /**
     * 获取hash数量
     */
    public Long getHashCount(String key) {
        return hashOperations.size(key);
    }

    //---------------------------------------------------------------------
    // ZSetOperations -> Redis Sort Set 操作
    //---------------------------------------------------------------------

    /**
     * 设置zset值
     */
    public boolean addZSetValue(String key, Object member, long score){
        return zSetOperations.add(key, member, score);
    }

    /**
     * 设置zset值
     */
    public boolean addZSetValue(String key, Object member, double score){
        return zSetOperations.add(key, member, score);
    }

    /**
     * 批量设置zset值
     */
    public long addBatchZSetValue(String key, Set<ZSetOperations.TypedTuple<Object>> tuples){
        return zSetOperations.add(key, tuples);
    }

    /**
     * 自增zset值
     */
    public void incZSetValue(String key, String member, long delta){
        zSetOperations.incrementScore(key, member, delta);
    }

    /**
     * 获取zset数量
     */
    public long getZSetScore(String key, String member){
        Double score = zSetOperations.score(key, member);
        if(score==null){
            return 0;
        }else{
            return score.longValue();
        }
    }

    /**
     * 获取有序集 key 中成员 member 的排名 。其中有序集成员按 score 值递减 (从小到大) 排序。
     */
    public Set<ZSetOperations.TypedTuple<Object>> getZSetRank(String key, long start, long end){
        return zSetOperations.rangeWithScores(key, start, end);
    }


    //---------------------------------------------------------------------
    // listOperations -> Redis List() 操作
    //---------------------------------------------------------------------

    /**
     * 添加list列表
     */
    public void addListValue(String key,Object list){
        listOperations.leftPush(key,list);
    }

    /**
     * 获取指定Key对应的list
     */
    public Object popListValue(String key){
        return listOperations.leftPop(key);
    }

    public Object getListValue(String key, long index) {
        return listOperations.index(key, index);
    }

    public Long getListCount(String key) {
        return listOperations.size(key);
    }
    //---------------------------------------------------------------------
    // setOperations -> Redis Set() 操作
    //---------------------------------------------------------------------

    /**
     * 添加Set集合集合
     */
    public void addSetValue(String key,Object list){
        setOperations.add(key,list);
    }

    /**
     * 获取指定Key对应的set
     */
    public Set<Object> getSetValue(String key){
        return setOperations.members(key);
    }

    /**
     * 获取并移除指定key的值
     */
    public Object popSetValue(String key){
        return setOperations.pop(key);
    }

    public Long getSetCount(String key) {
        return setOperations.size(key);
    }

}
