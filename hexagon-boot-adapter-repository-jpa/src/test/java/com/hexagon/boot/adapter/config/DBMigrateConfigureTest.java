package com.hexagon.boot.adapter.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration
@SpringBootTest(
        classes = {
                DBMigrateConfigure.class,
        },
        properties = {
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                "spring.datasource.username=sa",
                "spring.datasource.password=sa"
        })
@EnableAutoConfiguration
public class DBMigrateConfigureTest {

    @Autowired
    private JdbcTemplate template;

    @Test
    public void flyway() {
        assertEquals(this.template.<Integer>queryForObject("select count(*) from hibernate_sequence",
                Integer.class), new Integer(7));

        assertEquals(this.template.<Integer>queryForObject("select count(*) from flyway_test",
                Integer.class), new Integer(3));
    }
}