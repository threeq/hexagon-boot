package com.hexagon.boot.libs.lock;

/**
 * @author three
 */
@FunctionalInterface
public interface LockFactory {
    RLock getLock(String key);
}
