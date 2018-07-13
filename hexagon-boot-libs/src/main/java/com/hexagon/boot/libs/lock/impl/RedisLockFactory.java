package com.hexagon.boot.libs.lock.impl;

import com.hexagon.boot.libs.lock.LockFactory;
import com.hexagon.boot.libs.lock.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisLockFactory implements LockFactory {

    private RedissonClient redissonClient;
    @Autowired
    public RedisLockFactory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RLock getLock(String key) {
        return new RedisLock(this.redissonClient.getLock(key));
    }
}
