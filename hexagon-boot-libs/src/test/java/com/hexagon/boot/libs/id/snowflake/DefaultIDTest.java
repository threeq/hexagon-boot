package com.hexagon.boot.libs.id.snowflake;

import com.hexagon.boot.libs.TestConfiguration;
import com.hexagon.boot.libs.id.ID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.hexagon.boot.libs.id.snowflake.SnowflakeIDTest.verifyIncrement;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                TestConfiguration.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DefaultIDTest {

    @Autowired
    private ID id;

    @Autowired
    private TimeService timeService;
    @Autowired
    private WorkIDGetter workIDGetter;

    @Test
    void testIdIncrement() {

        Assert.assertTrue(timeService instanceof DefaultTimeService);
        Assert.assertTrue(workIDGetter instanceof SingleWorkIdGetter);

        verifyIncrement(id);
    }

}