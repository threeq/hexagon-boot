package com.hexagon.boot.adapter.repository;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.hexagon.boot.adapter.repository.mapper")
@Configuration
public class MybatisConfigure {
}
