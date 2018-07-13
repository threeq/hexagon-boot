package com.hexagon.boot.libs.lock.impl;

import com.hexagon.boot.libs.lock.RLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public class RedisLock implements RLock {

    private org.redisson.api.RLock redissonLock;
    public RedisLock(org.redisson.api.RLock redissonLock){
        this.redissonLock = redissonLock;
    }

    @Override
    public void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException {
        redissonLock.lockInterruptibly(leaseTime, unit);
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
        return redissonLock.tryLock(waitTime, leaseTime, unit);
    }

    @Override
    public void lock(long leaseTime, TimeUnit unit) {
        redissonLock.lock(leaseTime, unit);
    }

    @Override
    public void forceUnlock() {
        redissonLock.forceUnlock();
    }

    @Override
    public boolean isLocked() {
        return redissonLock.isLocked();
    }

    @Override
    public boolean isHeldByCurrentThread() {
        return redissonLock.isHeldByCurrentThread();
    }

    @Override
    public int getHoldCount() {
        return redissonLock.getHoldCount();
    }

    @Override
    public void lock() {
        redissonLock.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        redissonLock.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return redissonLock.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return redissonLock.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        redissonLock.unlock();
    }

    @Override
    public Condition newCondition() {
        return redissonLock.newCondition();
    }
}
