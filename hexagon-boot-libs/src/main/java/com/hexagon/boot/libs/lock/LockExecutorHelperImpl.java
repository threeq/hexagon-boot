package com.hexagon.boot.libs.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author three
 */
public class LockExecutorHelperImpl implements LockExecutorHelper {

    private static final Logger log = LoggerFactory.getLogger(DistributedLockAspect.class);

    private LockFactory lockFactory;
    @Autowired
    public LockExecutorHelperImpl(LockFactory lockFactory) {
        this.lockFactory = lockFactory;
    }

    public LockExecutor distributedLockExecutor(String lockKey, long timeout) {
        return executor -> {

            Object obj = null;
            log.debug("得到锁操作实例 {} ...", lockKey);
            RLock lock = lockFactory.getLock(lockKey);

            try {
                log.debug("尝试获取锁 {} ...", lockKey);
                lock.lock(timeout, TimeUnit.MILLISECONDS);
                log.debug("得到锁 {} ...", lockKey);
                obj = executor.execute(null);
            } finally {
                lock.unlock();
                log.debug("释放锁 {} ...", lockKey);
            }

            return obj;
        };
    }
}
