package com.hexagon.boot.libs.lock;

import com.hexagon.boot.libs.TestConfiguration;
import com.hexagon.boot.libs.TestExecutor;
import com.hexagon.boot.libs.lock.impl.RedisLockAutoConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author three
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                RedisLockAutoConfiguration.class,
                LockAutoConfiguration.class,
                DistributedLockAspect.class,
                DistributedLockTest.SyncExecutor.class,
                DistributedLockTest.SyncArgsExecutor.class,
                DistributedLockTest.NotSyncExecutor.class,
                TestService.class
        },
        properties = {
                "lock.enabled=true",
                "lock.distributed.redis.enabled=true"
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistributedLockTest {
    static Logger log = LoggerFactory.getLogger(DistributedLockTest.class);

    @EnableAutoConfiguration
    @Configuration
    @Component
    static class SyncExecutor implements TestExecutor {

        static Logger logger = LoggerFactory.getLogger(SyncExecutor.class);

        @Autowired
        private TestService service2;

        @DistributedLock(prefix = "test_distributed_lock", checkArgs = false)
        public void execute(String taskId) {
            service2.task(taskId);
        }
    }

    @Component
    static class SyncArgsExecutor implements TestExecutor {

        static Logger logger = LoggerFactory.getLogger(SyncArgsExecutor.class);

        @Autowired
        private TestService service2;

        @DistributedLock(prefix = "test_distributed_lock")
        public void execute(String taskId) {
            service2.task(taskId);
        }
    }

    @Component
    static class NotSyncExecutor implements TestExecutor {

        static Logger logger = LoggerFactory.getLogger(NotSyncExecutor.class);

        @Autowired
        private TestService service1;

        public void execute(String taskId) {
            service1.task(taskId);
        }
    }

    @Autowired
    private SyncExecutor syncExecutor;
    @Autowired
    private SyncArgsExecutor syncArgsExecutor;
    @Autowired
    private NotSyncExecutor notSyncExecutor;
    @Autowired
    private TestService service;

    @Before
    void before() {
        service.clearShareData();
    }

    @Test
    public void testDistributedSync() {
        List<Thread> threads = TestConfiguration.createThreads("sync-task", 2, syncExecutor);
        TestConfiguration.joinThreads(threads);

        String act = service.getShareData();
        log.info(act);
        String expected = "<12345678910><12345678910>";
        Assert.assertEquals(expected, act);
    }

    @Test
    public void testDistributedSyncArgs() {
        List<Thread> threads = TestConfiguration.createThreads("sync-task", 2, syncArgsExecutor);
        TestConfiguration.joinThreads(threads);

        String act = service.getShareData();
        log.info(act);
        String expected = "<12345678910><12345678910>";
        Assert.assertNotEquals(expected, act);
    }

    @Test
    public void testNotSync() {
        List<Thread> threads = TestConfiguration.createThreads("unsafe-sync-task", 2, notSyncExecutor);
        TestConfiguration.joinThreads(threads);

        String act = service.getShareData();
        log.info(act);
        String unexpected = "<12345678910><12345678910>";
        Assert.assertNotEquals(unexpected, act);
    }
}
