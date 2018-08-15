package com.hexagon.boot.generator.generator;

import com.hexagon.boot.generator.ConfigContext;
import com.hexagon.boot.generator.def.ClassDef;
import com.hexagon.boot.generator.utils.JavaCodeFormattingUtil;
import freemarker.template.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public abstract class AbstractGenerator {

    protected Configuration cfg(String path) throws IOException {
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        if (path.startsWith("classpath:")) {
            // 从哪里加载模板文件
            cfg.setClassLoaderForTemplateLoading(
                    ClassLoader.getSystemClassLoader(),
                    path.replace("classpath:", ""));
        } else {
            cfg.setDirectoryForTemplateLoading(new File(path));
        }


        // 设置对象包装器
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS));

        // 设置异常处理器
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    public abstract void generate(ConfigContext ctx) throws IOException;
    public boolean ignored(ConfigContext ctx) {return false;}

    protected void doGenerate(Template template, ClassDef def) throws IOException {

        String classPath = Optional.ofNullable(def.getOutputPath()).orElse(".") +
                "/" + def.getPackage().replaceAll("\\.", "/");
        Path path = Paths.get(classPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // 定义模板解释完成之后的输出
        JavaCodeFormattingUtil.Formatter out = new JavaCodeFormattingUtil.Formatter(new BufferedWriter(
                new FileWriter(new File(classPath + "/" +
                        Optional.ofNullable(def.getPrefix()).orElse("") +
                        def.getName() +
                        Optional.ofNullable(def.getSuffix()).orElse("") +
                        ".java"))));

        try {
            // 解释模板
            template.process(def, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    public AbstractGenerator config(ConfigContext ctx) {
        return this;
    };
}
