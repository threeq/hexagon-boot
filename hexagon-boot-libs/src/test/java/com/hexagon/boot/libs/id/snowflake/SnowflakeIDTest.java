package com.hexagon.boot.libs.id.snowflake;

import com.hexagon.boot.libs.TestConfiguration;
import com.hexagon.boot.libs.id.ID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                TestConfiguration.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SnowflakeIDTest {

    private static final Logger log = LoggerFactory.getLogger(SnowflakeIDTest.class);

    private TimeService timeService = new DefaultTimeService();

    @MockBean
    private TimeService mockTimeService;

    private List<Long> ids = new ArrayList<>();
    @Before
    void before() {
        ids.clear();
    }

    @Test
    void next() throws ParseException {
        SnowflakeID id = new SnowflakeID(1,1);
        id.setTimeService(timeService);
        verifyIncrement(id);
    }

    private synchronized void add(Collection<? extends Long> idList) {
        ids.addAll(idList);
    }

    @Test
    void syncTest() throws ParseException {
        SnowflakeID id = new SnowflakeID(1,1);
        id.setTimeService(timeService);

        int batch = 8;
        int countByBatch = 200 * 10000;
        List<Thread> threads = TestConfiguration.createThreads("id-next", batch, (taskId) -> {
            List<Long> localIds = new ArrayList<>(countByBatch);
            int i = 0;
            while (i < countByBatch) {
                localIds.add(id.next());
                i++;
            }

            log.info(taskId + " 生成了 "+i+" 个id");
            add(localIds);
        });
        TestConfiguration.joinThreads(threads);

        Assert.assertEquals(batch*countByBatch, ids.size());
        log.info("开始验证 id 唯一性");

        verifyReduplicated(ids);
    }

    @Test
    void testIncreasingTrend() throws ParseException {
        given(this.mockTimeService.time())
                .willReturn(100L, 100L, 100L, 100L, 100L, 100L, 100L, 100L, 100L, 101L, 101L);
        SnowflakeID idGen1 = new SnowflakeID(1,1);
        SnowflakeID idGen2 = new SnowflakeID(2,1);
        idGen1.setTimeService(mockTimeService);
        idGen2.setTimeService(mockTimeService);

        long one1 = idGen1.next(); // 100
        long two1 = idGen1.next(); // 100
        long nextTime1 = mockTimeService.time(); // 100

        long one2 = idGen2.next(); // 100
        long currentTime2 = mockTimeService.time(); // 100
        long two2 = idGen2.next(); // 100
        long nextTime2 = mockTimeService.time(); // 100

        long thr1 = idGen1.next(); // 100
        long currentTime3 = mockTimeService.time(); // 100

        long thr2 = idGen1.next(); // 101
        long currentTime4 = mockTimeService.time(); // 101

        verify(this.mockTimeService, times(11)).time();

        Assert.assertEquals(100L, nextTime1);
        Assert.assertEquals(100L, currentTime2);
        Assert.assertEquals(100L, nextTime2);
        Assert.assertEquals(100L, currentTime3);
        Assert.assertEquals(101L, currentTime4);

        Assert.assertTrue("错误："+one1+" 应该小于 "+two1,one1<two1);
        Assert.assertTrue("错误："+one2+" 应该小于 "+two2,one2<two2);

        Assert.assertTrue("错误："+one1+" 应该小于 "+one2, one1<one2);
        Assert.assertTrue("错误："+two1+" 应该小于 "+two2, two1<two2);

        Assert.assertEquals(currentTime2, nextTime1);
        Assert.assertTrue("错误："+two1+" 应该小于 "+one2, two1<one2);

        Assert.assertEquals(currentTime3, nextTime2);
        Assert.assertTrue("错误："+thr1+" 应该小于 "+two2, thr1<two2);

        Assert.assertTrue(currentTime4 > nextTime2);
        Assert.assertTrue("错误："+two2+" 应该小于 "+thr2, two2<thr2);

    }

    /**
     * 验证 id 唯一性
     * @param list
     */
    private static void verifyReduplicated(List<Long> list) {
        Collections.sort(list);

        int size = list.size();
        for(int i=0; i<size-1; i++) {
            Assert.assertNotEquals(list.get(i), list.get(i+1));
        }
    }

    /**
     * 校验 id 递增性
     * @param id
     */
    static void verifyIncrement(ID id) {
        int i=0;
        long one = id.next();
        while (i<10000000) {
            long two = id.next();
            Assert.assertTrue("在 "+i+" 错误："+one+" 应该小于 "+two, one<two);
            one = two;
            i++;
        }
    }
}