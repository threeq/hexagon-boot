package com.hexagon.boot.libs.id.snowflake;

import com.hexagon.boot.libs.TestConfiguration;
import com.hexagon.boot.libs.id.ID;
import com.hexagon.boot.libs.id.snowflake.zk.ZookeeperWorkIDGetter;
import org.apache.curator.utils.DefaultZookeeperFactory;
import org.apache.curator.utils.ZookeeperFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                TestConfiguration.class
        },
        properties = "app.id.snowflake.zk=127.0.0.1:2181",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZookeeperSnowflakeIDTest {

    @Autowired
    private Environment environment;

    @Autowired
    private ID id;

    @Autowired
    private TimeService timeService;
    @Autowired
    private WorkIDGetter workIDGetter;

    @Test
    void testIdIncrement() {
        Assert.assertTrue(timeService instanceof DefaultTimeService);
        Assert.assertTrue(workIDGetter instanceof ZookeeperWorkIDGetter);

        SnowflakeIDTest.verifyIncrement(id);
    }

    @Test
    void testWorkId() throws Exception {
        long workId1 = workIDGetter.getWorkId();
        long workId2 = workIDGetter.getWorkId();

        assertEquals(workId1, workId2);
    }

    @Test
    void testDataCenterId() throws Exception {
        long dc1 = workIDGetter.getDataCenterId();
        long dc2 = workIDGetter.getDataCenterId();

        assertEquals(dc1, dc2);
    }

    @Test
    void testMutilWorkId() throws Exception {

        ZookeeperFactory zkf = new DefaultZookeeperFactory();
        ZookeeperWorkIDGetter getter = new ZookeeperWorkIDGetter(0, "work_1",
                environment.getProperty("app.id.snowflake.zk"), zkf);
        ID lockId = new SnowflakeID(getter, new DefaultTimeService());

        getter.getDataCenterId();
        assertEquals(workIDGetter.getDataCenterId(), getter.getDataCenterId());
        assertTrue(workIDGetter.getWorkId()<getter.getWorkId());
        assertTrue(id.next() != lockId.next());

        getter.destroy();
    }

    @Test
    void testMutilDataCenter() throws Exception {

        ZookeeperFactory zkf = new DefaultZookeeperFactory();
        ZookeeperWorkIDGetter getter = new ZookeeperWorkIDGetter(1, "work_1",
                environment.getProperty("app.id.snowflake.zk"), zkf);
        ID lockId = new SnowflakeID(getter, new DefaultTimeService());

        assertTrue(workIDGetter.getDataCenterId()<getter.getDataCenterId());
        assertTrue(workIDGetter.getWorkId() == getter.getWorkId());
        assertTrue(id.next() != lockId.next());

        getter.destroy();
    }
}
