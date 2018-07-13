package com.hexagon.boot.libs.lock.impl;

import com.hexagon.boot.libs.lock.LockAutoConfiguration;
import com.hexagon.boot.libs.lock.LockFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.MsgPackJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Date 16/6/24
 * @User three
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@AutoConfigureBefore(LockAutoConfiguration.class)
@ConditionalOnProperty(name = "lock.distributed.redis.enabled")
public class RedisLockAutoConfiguration {

    @Autowired
    private Environment environment;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(RedisProperties redisProperties) {
        Config config = createConfig(redisProperties);
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnMissingBean(LockFactory.class)
    public LockFactory lockFactory(RedissonClient redissonClient) {
        return new RedisLockFactory(redissonClient);
    }

    private Config createConfig(RedisProperties redisProperties) {
        Config config = new Config();
        int poolSize = Integer.valueOf(this.environment.getProperty("redisson.pool.size", "10"));
        //sentinel
        if (redisProperties.getSentinel() != null) {
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(redisProperties.getSentinel().getMaster());
            redisProperties.getSentinel().getNodes();
            sentinelServersConfig.addSentinelAddress(redisProperties.getSentinel().getNodes().toArray(new String[]{}));
            sentinelServersConfig.setDatabase(redisProperties.getDatabase());

            sentinelServersConfig.setMasterConnectionPoolSize(poolSize);
            if (redisProperties.getPassword() != null) {
                sentinelServersConfig.setPassword(redisProperties.getPassword());
            }
        } else { //single server
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
            singleServerConfig.setDatabase(redisProperties.getDatabase());
            singleServerConfig.setConnectionPoolSize(poolSize);
            if (redisProperties.getPassword() != null) {
                singleServerConfig.setPassword(redisProperties.getPassword());
            }
        }

        config.setCodec(new MsgPackJacksonCodec());
        return config;
    }
}
