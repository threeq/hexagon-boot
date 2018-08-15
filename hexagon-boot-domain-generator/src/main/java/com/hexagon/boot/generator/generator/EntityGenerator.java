package com.hexagon.boot.generator.generator;

import com.hexagon.boot.generator.ConfigContext;
import com.hexagon.boot.generator.def.EntityDef;
import freemarker.template.Template;

import java.io.IOException;

public class EntityGenerator extends AbstractGenerator {
    @Override
    public void generate(ConfigContext ctx) throws IOException {
        Template template = cfg(ctx.getTemplatesPath()).getTemplate("Entity.java.ftl");

        EntityDef def = new EntityDef(ctx.getEntityPackage(), ctx.getEntityName(), ctx.getParent("entity"));
        def.setUseLombok(Boolean.valueOf(ctx.getEntityConfig("entity.useLombok", "false")));
        def.setUseBuilder(Boolean.valueOf(ctx.getEntityConfig("entity.useBuilder", "false")));

        if(def.isUseLombok()) {
            def.addImport("lombok.Getter","lombok.Setter");
        }
        if(def.isUseBuilder()) {
            def.addImport("lombok.Builder");
        }

        def.addField(ctx.getEntityFields());

        def.setCtx(ctx.getSharedCtx());
        def.setOutputPath(ctx.getEntityOutputPath());
        doGenerate(template, def);
    }

}
