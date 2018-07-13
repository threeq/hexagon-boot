package com.hexagon.boot.libs.lock;

/**
 * three
 * @param <T>
 */
@FunctionalInterface
public interface LockExecutor<T> {
    T execute(LockExecutor executor) throws Throwable;
}
