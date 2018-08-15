package com.hexagon.boot.generator;

import com.hexagon.boot.generator.def.ClassDef;
import com.hexagon.boot.generator.def.FieldDef;
import javafx.collections.FXCollections;
import lombok.Getter;

import java.io.IOException;
import java.util.*;

@Getter
public class ConfigContext {
    private String templatesPath;

    private String entityName;
    private String entityOutputPath;
    private String entityPackage;

    private final Map<String, String> parentsDef = new HashMap<>();
    private final Properties properties;
    /**
     * 在配置阶段，各个 generator 可以写入数据到这里，其他 generator 可以使用
     */
    private Map<String, Map<String, Object>> sharedCtx = new HashMap<>();
    private List<FieldDef> fields;

    public ConfigContext(String path) throws IOException {
        fields = new ArrayList<>();
        properties = new Properties();
        properties.load(ConfigContext.class.getClassLoader().getResourceAsStream(path));

        Enumeration<?> keys = properties.propertyNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement().toString();
            if (key.startsWith("parents")) {
                parentsDef.put(key.split("\\.")[1], properties.getProperty(key));
            }
        }

        this.entityOutputPath = properties.getProperty("entity.output.path", "src/test/java");
        this.entityPackage = properties.getProperty("entity.output.package", "com.example.entity");
        this.templatesPath = properties.getProperty("path.templates", "classpath:defaults");
    }

    public String getParent(String p) {
        return parentsDef.get(p);
    }

    public String getEntityConfig(String k, String defaultValue) {
        if (!k.startsWith("entity.")) {
            throw new IllegalArgumentException("entity config key error: " + k);
        }
        return properties.getProperty(k, defaultValue);
    }

    public String getExtConfig(String k, String defaultValue) {
        if (!k.startsWith("ext.")) {
            throw new IllegalArgumentException("ext config key error: " + k);
        }
        return properties.getProperty(k, defaultValue);
    }

    public ConfigContext setEntityName(String name) {
        this.entityName = name;
        return this;
    }

    public ConfigContext setEntityPackage(String toPackage) {
        this.entityPackage = toPackage;
        return this;
    }

    public ConfigContext setEntityOutputPath(String outputPath) {
        this.entityOutputPath = outputPath;
        return this;
    }

    public FieldDef[] getEntityFields() {
        return this.fields.toArray(new FieldDef[0]);
    }

    public ConfigContext addField(FieldDef ... imports) {
        this.fields.addAll(Arrays.asList(imports));
        return this;
    }
}
