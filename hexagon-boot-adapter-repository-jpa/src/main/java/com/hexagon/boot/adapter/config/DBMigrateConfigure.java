package com.hexagon.boot.adapter.config;

import lombok.extern.java.Log;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Log
public class DBMigrateConfigure {


    @Bean(initMethod = "migrate")
    @Order(Ordered.LOWEST_PRECEDENCE)
    public Flyway flyway(DataSource dataSource, DataSourceProperties properties) throws SQLException {
        Flyway flyway=new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations(
                String.format(
                        "classpath:/flyway/db/%1$s/migration",
                        databaseType(properties)
                )
        );
        return flyway;
    }

    private Object databaseType(DataSourceProperties dataSource) throws SQLException {
        Map<String, String> dbType = new HashMap<>();
        dbType.put("com.mysql.jdbc.Driver", "mysql");
        dbType.put("oracle.jdbc.driver.OracleDriver", "oracle");
        dbType.put("org.h2.Driver", "h2");

        log.info("Using database driver: " + dataSource.getDriverClassName());

        return dbType.get(dataSource.getDriverClassName());
    }

}

