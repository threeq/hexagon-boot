package com.hexagon.boot.generator.generator;

import com.hexagon.boot.generator.ConfigContext;
import com.hexagon.boot.generator.def.ClassDef;
import freemarker.template.Template;

import java.io.IOException;

public class ServiceGenerator extends AbstractGenerator {
    @Override
    public void generate(ConfigContext ctx) throws IOException {
        Template template = cfg(ctx.getTemplatesPath()).getTemplate("Service.java.ftl");

        ClassDef def = new ClassDef(ctx.getEntityPackage(), ctx.getEntityName(), ctx.getParent("service"));
        def.setSuffix("ApplicationService");
        def.setCtx(ctx.getSharedCtx());
        def.setOutputPath(ctx.getEntityOutputPath());

        doGenerate(template, def);
    }
}
