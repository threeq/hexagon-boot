package com.hexagon.boot.libs.cache;

import com.hexagon.boot.libs.cache.redis.JsonSerializationPair;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by three on 16/2/27.
 */
@EnableCaching
@Configuration
@ConditionalOnProperty(name = "spring.redis.host")
public class RedisCacheConfig {

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedisConnectionFactory redisConnectionFactory() {
        RedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        return redisConnectionFactory;
    }

    /**
     * redis 缓存注解序列化方式
     *
     * @param factory
     * @return
     */
    @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisConnectionFactory factory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("", RedisCacheConfiguration.defaultCacheConfig());

        RedisCacheManager rcm = RedisCacheManager.builder(factory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                        .serializeValuesWith(new JsonSerializationPair<>()))
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();

        return rcm;
    }


}
