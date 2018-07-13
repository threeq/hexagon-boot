package com.hexagon.boot.libs.id.snowflake;

import com.hexagon.boot.libs.id.ID;
import com.hexagon.boot.libs.id.snowflake.zk.ZookeeperWorkIDGetter;
import org.apache.curator.utils.DefaultZookeeperFactory;
import org.apache.curator.utils.ZookeeperFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@Configuration
@ConditionalOnProperty(prefix = "app.id.snowflake.enable", value = "true", matchIfMissing = true)
public class SnowflakeAutoConfiguration {

    @Bean
    public ID id(WorkIDGetter workIDGetter, TimeService timeService) throws Exception {
        return new SnowflakeID(workIDGetter, timeService);
    }

    @Bean
    @ConditionalOnMissingBean(ZookeeperFactory.class)
    public ZookeeperFactory zookeeperFactory() {
        return new DefaultZookeeperFactory();
    }

    @Bean
    @ConditionalOnProperty(value = "app.id.snowflake.zk")
    public WorkIDGetter wrkIDGetterByZookeeper(Environment environment, ZookeeperFactory zookeeperFactory) throws Exception {
        int dataCenter = environment.getProperty("app.id.snowflake.dc", Integer.class, 0);
        int serverPort = 7609;
        final String nodeInfo = InetAddress.getLocalHost().getHostAddress()+":"+ serverPort;
        return new ZookeeperWorkIDGetter(dataCenter, nodeInfo,
                environment.getProperty("app.id.snowflake.zk"),
                zookeeperFactory);
    }

    @Bean
    @ConditionalOnMissingBean(WorkIDGetter.class)
    public WorkIDGetter workIDGetter() {
        return new SingleWorkIdGetter();
    }

    @Bean
    public TimeService timeService() {
        return new DefaultTimeService();
    }
}
