package com.hexagon.boot.generator.generator;

import com.hexagon.boot.generator.ConfigContext;
import com.hexagon.boot.generator.def.ClassDef;
import freemarker.template.Template;

import java.io.IOException;

public class RepositoryGenerator extends AbstractGenerator {
    @Override
    public void generate(ConfigContext ctx) throws IOException {
        Template template = cfg(ctx.getTemplatesPath()).getTemplate("Repository.java.ftl");

        ClassDef def = new ClassDef(ctx.getEntityPackage(), ctx.getEntityName(), ctx.getParent("repository"));
        def.setSuffix("Repository");
        def.setCtx(ctx.getSharedCtx());
        def.setOutputPath(ctx.getEntityOutputPath());

        doGenerate(template, def);
    }
}
