package com.hexagon.boot.generator.generator.ext;

import com.hexagon.boot.generator.ConfigContext;
import com.hexagon.boot.generator.generator.AbstractGenerator;

import java.io.IOException;

public class MybatisImplGenerator extends AbstractGenerator {
    @Override
    public boolean ignored(ConfigContext ctx) {
        return Boolean.valueOf(ctx.getExtConfig("ext.mybatis.ignored", "false"));
    }

    @Override
    public void generate(ConfigContext ctx) throws IOException {

    }
}
