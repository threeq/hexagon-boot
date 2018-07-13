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
        classes = {RedisLockAutoConfiguration.class,
                LockAutoConfiguration.class,
                DistributedLockAspect.class,
                TestService.class,
                LockExecutorHelperTest.SyncExecutor.class,
                LockExecutorHelperTest.SyncUnsafeExecutor.class
        },
        properties = {
                "lock.enabled=true",
                "lock.distributed.redis.enabled=true"
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LockExecutorHelperTest {
    static Logger log = LoggerFactory.getLogger(LockExecutorHelperTest.class);

    @EnableAutoConfiguration
    @Configuration
    @Component
    static class SyncExecutor implements TestExecutor {

        static Logger logger = LoggerFactory.getLogger(SyncExecutor.class);

        @Autowired
        private LockExecutorHelper lockExecutorHelper;
        @Autowired
        private TestService service1;

        public void execute(String taskId) {
            try {
                lockExecutorHelper.distributedLockExecutor("sync-helper-task", 3000)
                        .execute(executor -> {
                            this.unsafeExecute(taskId);
                            return "";
                        });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        private void unsafeExecute(String taskId) {
            service1.task(taskId);
        }
    }

    @Component
    static class SyncUnsafeExecutor implements TestExecutor {

        static Logger logger = LoggerFactory.getLogger(SyncExecutor.class);

        @Autowired
        private TestService testService;

        public void execute(String taskId) {
            this.unsafeExecute(taskId);
        }

        private void unsafeExecute(String taskId) {
            testService.task(taskId);
        }
    }



    @Autowired
    private SyncExecutor syncExecutor;
    @Autowired
    private SyncUnsafeExecutor syncUnsafeExecutor;
    @Autowired
    private TestService service;

    @Before
    void before() {
        service.clearShareData();
    }

    @Test
    void distributedLockExecutor() {
        List<Thread> threads = TestConfiguration.createThreads("sync-helper-task", 2, syncExecutor);
        TestConfiguration.joinThreads(threads);

        String act = service.getShareData();
        log.info(act);
        String expected = "<12345678910><12345678910>";
        Assert.assertEquals(expected, act);
    }

    @Test
    void unsafeExecutor() {
        List<Thread> threads = TestConfiguration.createThreads("sync-unsafe-task", 2, syncUnsafeExecutor);
        TestConfiguration.joinThreads(threads);

        String act = service.getShareData();
        log.info(act);
        String unexpected = "<12345678910><12345678910>";
        Assert.assertNotEquals(unexpected, act);
    }

}