package com.hexagon.boot.libs.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                RedisListCacheTest.Configuration.class,
                RedisCacheConfig.class,
                RedisListCacheTest.CacheService.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisListCacheTest {

    @SpringBootApplication
    static class Configuration { }

    @Component
    static class CacheService {

        @Cacheable(
                value = "map",
                key = "#id"
        )
        public Map<String, Object> getMap(String id) {
            Map<String, Object> data = new HashMap<>();
            data.put("k1", "v1");
            data.put("k2", "v2");
            data.put("k3", "v3");
            return data;
        }

        @Cacheable(
                value = "map",
                key = "#id",
                unless = "#result == null"
        )
        public Map<String, Object> getMapCache(String id) {
            return null;
        }
    }

    @Autowired
    private CacheService cacheService;

    @Test
    public void testCacheConfig() throws JsonProcessingException {
        Map<String, Object> expect = cacheService.getMap("map1");
        Map<String, Object> act = cacheService.getMapCache("map1");
        Map<String, Object> actNull = cacheService.getMapCache("map_null");

        ObjectMapper om = new ObjectMapper();
        System.out.println(om.writeValueAsString(expect));
        System.out.println(om.writeValueAsString(act));

        Assert.assertArrayEquals(expect.keySet().toArray(),act.keySet().toArray());
        Assert.assertArrayEquals(expect.values().toArray(),act.values().toArray());
        Assert.assertNull(actNull);
    }
}
