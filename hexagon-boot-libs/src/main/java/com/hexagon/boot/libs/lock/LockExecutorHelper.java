package com.hexagon.boot.libs.lock;

@FunctionalInterface
public interface LockExecutorHelper {
    LockExecutor distributedLockExecutor(String lockKey, long timeout);
}
