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
                DistributedLockAspect.class,
                LockAutoConfiguration.class,
                TestService.class,
                DistributedPrivateMehtodLockTest.SyncExecutor.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistributedPrivateMehtodLockTest {
    static Logger log = LoggerFactory.getLogger(DistributedPrivateMehtodLockTest.class);

    @EnableAutoConfiguration
    @Configuration
    @Component
    static class SyncExecutor implements TestExecutor {

        static Logger logger = LoggerFactory.getLogger(SyncExecutor.class);

        @Autowired
        private TestService service1;

        public void execute(String taskId) {
            this.syncExecute(taskId);
        }

        @DistributedLock(prefix = "test_distributed_lock_args")
        private void syncExecute(String taskId) {
            service1.task(taskId);
        }
    }


    @Autowired
    private SyncExecutor syncExecutor;
    @Autowired
    private TestService service;

    @Before
    void before() {
        service.clearShareData();
    }

    @Test
    public void testDistributedSync() {
        List<Thread> threads = TestConfiguration.createThreads("private-method-annotaion-unsafe-sync-task", 2, syncExecutor);
        TestConfiguration.joinThreads(threads);

        String act = service.getShareData();
        log.info(act);
        String unexpected = "<12345678910><12345678910>";
        Assert.assertNotEquals(unexpected, act);
    }


}
