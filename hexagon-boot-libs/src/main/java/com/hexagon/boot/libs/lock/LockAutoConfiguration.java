package com.hexagon.boot.libs.lock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "lock.enabled")
public class LockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LockExecutorHelper.class)
    public LockExecutorHelper lockExecutorHelper(LockFactory lockFactory) {
        return new LockExecutorHelperImpl(lockFactory);
    }
}
