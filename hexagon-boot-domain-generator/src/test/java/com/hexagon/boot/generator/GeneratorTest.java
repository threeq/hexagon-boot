package com.hexagon.boot.generator;

import com.hexagon.boot.generator.def.FieldDef;
import com.hexagon.boot.generator.generator.EntityGenerator;
import com.hexagon.boot.generator.generator.RepositoryGenerator;
import com.hexagon.boot.generator.generator.ServiceGenerator;
import org.junit.Test;

import java.io.IOException;

public class GeneratorTest {

    @Test
    public void testFreemarker() throws IOException {
        new Generator()
                .context(new ConfigContext("defaults/config.properties")
                        .setEntityOutputPath("../hexagon-boot-domain-sys/src/main/java")
                        .setEntityPackage("test.generator")
                        .setEntityName("Abc")
                        .addField(
                                new FieldDef("int", "test1"),
                                new FieldDef("String", "test2"),
                                new FieldDef("java.util.Date", "test3")
                        )
                )
                .addGenerator(new EntityGenerator())
                .addGenerator(new ServiceGenerator())
                .addGenerator(new RepositoryGenerator())
                .generate();
    }


}
