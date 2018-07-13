package com.hexagon.boot.libs.cache;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                RedisUtilTest.TestApp.class,
                RedisDataConfig.class,
                RedisUtil.class,
        },
        properties = {
                "spring.redis.host=127.0.0.1"
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedisUtilTest {

    private static final String EXIST_VALUE_KEY = "EXIST_VALUE_KEY";
    private static final String NOT_EXIST_VALUE_KEY = "NOT_EXIST_VALUE_KEY";
    private static final String RENAME_VALUE_KEY = "RENAME_VALUE_KEY";

    private static final String EXIST_HASH_KEY = "EXIST_HASH_KEY";
    private static final String EXIST_HASH_SUB_KEY = "EXIST_HASH_SUB_KEY";
    private static final String NOT_EXIST_HASH_KEY = "NOT_EXIST_HASH_KEY";
    private static final String ADD_HASH_KEY = "ADD_HASH_KEY";

    private static final String EXIST_ZSET_KEY = "EXIST_ZSET_KEY";

    private static final String EXIST_LIST_KEY = "EXIST_LIST_KEY";
    private static final String EXIST_SET_KEY = "EXIST_SET_KEY";

    @SpringBootApplication
    static class TestApp {}

    @Autowired
    private RedisUtil redisUtil;

    @Before
    void before() {
        redisUtil.addValue(EXIST_VALUE_KEY, "EXIST_VALUE_KEY");
        redisUtil.addHashValue(EXIST_HASH_KEY, EXIST_HASH_SUB_KEY, EXIST_HASH_SUB_KEY, 10000);
    }

    @After
    void after() {
        redisUtil.delete(EXIST_VALUE_KEY);
        redisUtil.delete(EXIST_HASH_KEY);
        redisUtil.delete(RENAME_VALUE_KEY);
        redisUtil.delete(EXIST_ZSET_KEY);
        redisUtil.delete(EXIST_LIST_KEY);
        redisUtil.delete(EXIST_SET_KEY);
    }

    private boolean verifyKeyNotExist(String key) {
        Assert.assertEquals(false,redisUtil.hasKey(key));
        Assert.assertEquals(null,redisUtil.getValue(key));
        return true;
    }

    private void verifyKeyExist(String key, String value) {
        Assert.assertEquals(true,redisUtil.hasKey(key));
        Assert.assertEquals(value,redisUtil.getValue(key));
    }

    @Test
    void hasKey() {
        Assert.assertEquals(true,redisUtil.hasKey(EXIST_VALUE_KEY));
        verifyKeyNotExist(NOT_EXIST_VALUE_KEY);
    }

    @Test
    void hasKey1() {
        Assert.assertEquals(true,redisUtil.hasKey(EXIST_HASH_KEY, EXIST_HASH_SUB_KEY));

        Assert.assertEquals(false,redisUtil.hasKey(EXIST_HASH_KEY, NOT_EXIST_HASH_KEY));
        Assert.assertEquals(false,redisUtil.hasKey(NOT_EXIST_HASH_KEY, EXIST_HASH_SUB_KEY));
    }

    @Test
    void renameKey() {
        verifyKeyExist(EXIST_VALUE_KEY, EXIST_VALUE_KEY);

        redisUtil.renameKey(EXIST_VALUE_KEY, RENAME_VALUE_KEY);

        verifyKeyNotExist(EXIST_VALUE_KEY);
        verifyKeyExist(RENAME_VALUE_KEY, EXIST_VALUE_KEY);
    }

    @Test
    void delete() {
        verifyKeyExist(EXIST_VALUE_KEY, EXIST_VALUE_KEY);
        redisUtil.delete(EXIST_VALUE_KEY);
        verifyKeyNotExist(EXIST_VALUE_KEY);
    }

    @Test
    void expire() {
        verifyKeyExist(EXIST_VALUE_KEY, EXIST_VALUE_KEY);

        redisUtil.expire(EXIST_VALUE_KEY, 1, TimeUnit.MILLISECONDS);

        await().atMost(2, SECONDS).until(awaitVerify());  // Compliant
    }

    private Callable<Boolean> awaitVerify() {
        return () -> verifyKeyNotExist(EXIST_VALUE_KEY);
    }

    @Test
    void ttl() {
        verifyKeyExist(EXIST_VALUE_KEY, EXIST_VALUE_KEY);

        redisUtil.expire(EXIST_VALUE_KEY, 10, SECONDS);
        long ttl = redisUtil.ttl(EXIST_VALUE_KEY);
        Assert.assertEquals(10, ttl);

        redisUtil.expire(EXIST_VALUE_KEY, 100000, TimeUnit.MILLISECONDS);
        ttl = redisUtil.ttl(EXIST_VALUE_KEY);
        Assert.assertEquals(100, ttl);
    }

    @Test
    void keys() {
        Set<String> keys = redisUtil.keys("*");
        Assert.assertTrue(keys.size()>=2);

        keys = redisUtil.keys("EXIST_VALUE_*");
        Assert.assertArrayEquals(new String[]{EXIST_VALUE_KEY}, keys.toArray());

        keys = redisUtil.keys("NOT_EXIST_VALUE_*");
        Assert.assertEquals(0, keys.size());
    }

    @Test
    void delete1() {
        verifyKeyExist(EXIST_VALUE_KEY, EXIST_VALUE_KEY);

        Set<String> keys = new HashSet<>();
        keys.add(EXIST_VALUE_KEY);
        keys.add(EXIST_HASH_KEY);
        redisUtil.delete(keys);

        verifyKeyNotExist(EXIST_VALUE_KEY);
        verifyKeyNotExist(EXIST_HASH_KEY);
    }

    @Test
    void addValue() {
        redisUtil.delete(EXIST_VALUE_KEY);
        verifyKeyNotExist(EXIST_VALUE_KEY);

        redisUtil.addValue(EXIST_VALUE_KEY, EXIST_VALUE_KEY);
        verifyKeyExist(EXIST_VALUE_KEY, EXIST_VALUE_KEY);
    }

    @Test
    void addValue1() {
        redisUtil.delete(EXIST_VALUE_KEY);
        verifyKeyNotExist(EXIST_VALUE_KEY);

        redisUtil.addValue(EXIST_VALUE_KEY, EXIST_VALUE_KEY, 10);
        verifyKeyExist(EXIST_VALUE_KEY, EXIST_VALUE_KEY);
        Assert.assertEquals(10, redisUtil.ttl(EXIST_VALUE_KEY));
    }

    @Test
    void addValue2() {
        redisUtil.delete(EXIST_VALUE_KEY);
        verifyKeyNotExist(EXIST_VALUE_KEY);

        redisUtil.addValue(EXIST_VALUE_KEY, EXIST_VALUE_KEY, 10, TimeUnit.MINUTES);
        verifyKeyExist(EXIST_VALUE_KEY, EXIST_VALUE_KEY);
        Assert.assertEquals(10*60, redisUtil.ttl(EXIST_VALUE_KEY));
    }

    @Test
    void getValue() {
        verifyKeyExist(EXIST_VALUE_KEY, EXIST_VALUE_KEY);
        verifyKeyNotExist(NOT_EXIST_VALUE_KEY);
    }

    @Test
    void addHashValue() {
        Assert.assertEquals(1, redisUtil.getHashCount(EXIST_HASH_KEY).longValue());
        redisUtil.addHashValue(EXIST_HASH_KEY, ADD_HASH_KEY, ADD_HASH_KEY, -1);
        Assert.assertEquals(2, redisUtil.getHashCount(EXIST_HASH_KEY).longValue());
    }

    @Test
    void addAllHashValue() {
        Assert.assertEquals(1, redisUtil.getHashCount(EXIST_HASH_KEY).longValue());
        Map<String, Object> allKeys = new HashMap<>();
        allKeys.put("1", "1");
        allKeys.put("2", "2");
        allKeys.put("3", "3");
        redisUtil.addAllHashValue(EXIST_HASH_KEY, allKeys, -1);
        Assert.assertEquals(4, redisUtil.getHashCount(EXIST_HASH_KEY).longValue());
    }

    @Test
    void deleteHashValueAndHashCount() {
        Assert.assertEquals(1, redisUtil.getHashCount(EXIST_HASH_KEY).longValue());
        redisUtil.addHashValue(EXIST_HASH_KEY, ADD_HASH_KEY, ADD_HASH_KEY, -1);
        Assert.assertEquals(2, redisUtil.getHashCount(EXIST_HASH_KEY).longValue());

        redisUtil.deleteHashValue(EXIST_HASH_KEY, EXIST_HASH_SUB_KEY);
        Assert.assertEquals(1, redisUtil.getHashCount(EXIST_HASH_KEY).longValue());

        redisUtil.deleteHashValue(EXIST_HASH_KEY, ADD_HASH_KEY);
        Assert.assertEquals(0, redisUtil.getHashCount(EXIST_HASH_KEY).longValue());
    }

    @Test
    void getHashValue() {
        Assert.assertEquals(EXIST_HASH_SUB_KEY, redisUtil.getHashValue(EXIST_HASH_KEY, EXIST_HASH_SUB_KEY));
        Assert.assertEquals(null, redisUtil.getHashValue(EXIST_HASH_KEY, NOT_EXIST_HASH_KEY));
    }

    @Test
    void getHashAllValue() {
        String[] values = redisUtil.getHashAllValue(EXIST_HASH_KEY).toArray(new String[0]);

        Assert.assertArrayEquals(new String[]{EXIST_HASH_SUB_KEY}, values);

        Map<String, Object> allKeys = new HashMap<>();
        allKeys.put("1", "1");
        allKeys.put("2", "2");
        allKeys.put("3", "3");
        redisUtil.addAllHashValue(EXIST_HASH_KEY, allKeys, -1);

        values = redisUtil.getHashAllValue(EXIST_HASH_KEY).toArray(new String[0]);
        Assert.assertArrayEquals(new String[]{EXIST_HASH_SUB_KEY, "1", "2", "3"}, values);
    }

    @Test
    void getHashMultiValue() {
        Map<String, Object> allKeys = new HashMap<>();
        allKeys.put("1", "1");
        allKeys.put("2", "2");
        allKeys.put("3", "3");
        redisUtil.addAllHashValue(EXIST_HASH_KEY, allKeys, -1);

        List<String> keys = new ArrayList<>();
        keys.add("2");
        keys.add("3");
        String[] values = redisUtil.getHashMultiValue(EXIST_HASH_KEY, keys).toArray(new String[0]);
        Assert.assertArrayEquals(new String[]{"2", "3"}, values);
    }

    @Test
    void addZSetValue() {

        boolean result = redisUtil.addZSetValue(EXIST_ZSET_KEY, EXIST_ZSET_KEY, 1);
        Assert.assertEquals(true, result);
    }

    @Test
    void addZSetValue1() {
        boolean result = redisUtil.addZSetValue(EXIST_ZSET_KEY, EXIST_ZSET_KEY, 2.1);
        Assert.assertEquals(true, result);
    }

    @Test
    void addBatchZSetValue() {
        Set<ZSetOperations.TypedTuple<Object>> values = new HashSet<>();
        values.add(new DefaultTypedTuple<>("111", 1.1));
        values.add(new DefaultTypedTuple<>("111", 1.1));
        values.add(new DefaultTypedTuple<>("222", 1.1));
        values.add(new DefaultTypedTuple<>("333", 1.1));
        long result = redisUtil.addBatchZSetValue(EXIST_ZSET_KEY, values);
        Assert.assertEquals(3, result);
    }

    @Test
    void incZSetValue() {
        Set<ZSetOperations.TypedTuple<Object>> values = new HashSet<>();
        values.add(new DefaultTypedTuple<>("1", 1.1));
        long result = redisUtil.addBatchZSetValue(EXIST_ZSET_KEY, values);
        Assert.assertEquals(1, result);

        redisUtil.incZSetValue(EXIST_ZSET_KEY, "1", 2);
        Assert.assertEquals(3.0, redisUtil.getZSetScore(EXIST_ZSET_KEY, "1"));
    }

    @Test
    void getZSetScore() {
        Set<ZSetOperations.TypedTuple<Object>> values = new HashSet<>();
        values.add(new DefaultTypedTuple<>("1", 1.1));
        long result = redisUtil.addBatchZSetValue(EXIST_ZSET_KEY, values);
        Assert.assertEquals(1.0, result);

        Assert.assertEquals(1.0, redisUtil.getZSetScore(EXIST_ZSET_KEY, "1"));
        Assert.assertEquals(0, redisUtil.getZSetScore(EXIST_ZSET_KEY, "2"));
    }

    @Test
    void getZSetRank() {
        Set<ZSetOperations.TypedTuple<Object>> values = new HashSet<>();
        values.add(new DefaultTypedTuple<>("111", 1.0));
        values.add(new DefaultTypedTuple<>("222", 3.0));
        values.add(new DefaultTypedTuple<>("333", 5.0));
        values.add(new DefaultTypedTuple<>("444", 7.0));
        long result = redisUtil.addBatchZSetValue(EXIST_ZSET_KEY, values);
        Assert.assertEquals(4, result);

        values = redisUtil.getZSetRank(EXIST_ZSET_KEY, 2, 4);
        Assert.assertEquals(2, values.size());

        ZSetOperations.TypedTuple[] array = values.toArray(new ZSetOperations.TypedTuple[0]);
        Assert.assertEquals("333",array[0].getValue());
        Assert.assertEquals(5.0, Objects.requireNonNull(array[0].getScore()).doubleValue());
        Assert.assertEquals("444",array[1].getValue());
        Assert.assertEquals(7.0, Objects.requireNonNull(array[1].getScore()).doubleValue());
    }

    @Test
    void addListValue() {
        redisUtil.addListValue(EXIST_LIST_KEY, "1");
        Assert.assertEquals(1, redisUtil.getListCount(EXIST_LIST_KEY).longValue());
    }

    @Test
    void getListValue() {
        redisUtil.addListValue(EXIST_LIST_KEY, "1");
        redisUtil.addListValue(EXIST_LIST_KEY, "2");
        redisUtil.addListValue(EXIST_LIST_KEY, "3");
        Assert.assertEquals(3, redisUtil.getListCount(EXIST_LIST_KEY).longValue());

        Object values = redisUtil.getListValue(EXIST_LIST_KEY, 2);
        Assert.assertEquals("1", values);
        values = redisUtil.getListValue(EXIST_LIST_KEY, 3);
        Assert.assertEquals(null, values);
        Assert.assertEquals(3, redisUtil.getListCount(EXIST_LIST_KEY).longValue());

        values = redisUtil.popListValue(EXIST_LIST_KEY);
        Assert.assertEquals("3", values);
        Assert.assertEquals(2, redisUtil.getListCount(EXIST_LIST_KEY).longValue());
    }

    @Test
    void addSetValue() {
        redisUtil.addSetValue(EXIST_SET_KEY, "1");
        Assert.assertEquals(1, redisUtil.getSetCount(EXIST_SET_KEY).longValue());

    }

    @Test
    void getSetValue() {
        redisUtil.addSetValue(EXIST_SET_KEY, "1");
        redisUtil.addSetValue(EXIST_SET_KEY, "2");
        redisUtil.addSetValue(EXIST_SET_KEY, "2");
        redisUtil.addSetValue(EXIST_SET_KEY, "3");
        Assert.assertEquals(3, redisUtil.getSetCount(EXIST_SET_KEY).longValue());

        Set<Object> values = redisUtil.getSetValue(EXIST_SET_KEY);
        Assert.assertEquals(3, values.size());
        Assert.assertEquals(3, redisUtil.getSetCount(EXIST_SET_KEY).longValue());
    }

    @Test
    void popSetValue() {
        redisUtil.addSetValue(EXIST_SET_KEY, "1");
        redisUtil.addSetValue(EXIST_SET_KEY, "2");
        redisUtil.addSetValue(EXIST_SET_KEY, "3");
        Assert.assertEquals(3, redisUtil.getSetCount(EXIST_SET_KEY).longValue());

        Object values = redisUtil.popSetValue(EXIST_SET_KEY);
        Assert.assertNotNull(values);
        Assert.assertEquals(2, redisUtil.getSetCount(EXIST_SET_KEY).longValue());

    }
}
