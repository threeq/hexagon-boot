package com.hexagon.boot.generator;

import com.hexagon.boot.generator.generator.AbstractGenerator;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Generator {

    private ConfigContext ctx;
    private List<AbstractGenerator> generators = new LinkedList<>();

    public void generate() {

        try {

            for (AbstractGenerator generator : generators) {
                if (generator.ignored(ctx)) {
                    continue;
                }
                generator.generate(ctx);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Generator addGenerator(AbstractGenerator generator) {
        assert this.ctx != null;
        generators.add(generator.config(ctx));
        return this;
    }

    public Generator context(ConfigContext configContext) {
        this.ctx = configContext;
        return this;
    }
}
