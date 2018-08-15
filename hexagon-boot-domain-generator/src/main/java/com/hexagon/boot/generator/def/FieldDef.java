package com.hexagon.boot.generator.def;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class FieldDef {
    private String visible;
    private String type;
    private String name;

    private String importType;

    private static final Map<String, String[]> BASIC_TYPE_DEF = new HashMap<>();

    static {
        BASIC_TYPE_DEF.put("string", new String[]{"java.lang.String", "String"});
        BASIC_TYPE_DEF.put("char", new String[]{"java.lang.Character", "Character"});
        BASIC_TYPE_DEF.put("date", new String[]{"java.util.Date", "Date"});
        BASIC_TYPE_DEF.put("boolean", new String[]{"java.lang.Boolean", "Boolean"});

        BASIC_TYPE_DEF.put("int", new String[]{"java.lang.Integer", "Integer"});
        BASIC_TYPE_DEF.put("Integer", new String[]{"java.lang.Integer", "Integer"});
        BASIC_TYPE_DEF.put("long", new String[]{"java.lang.Long", "Long"});
        BASIC_TYPE_DEF.put("float", new String[]{"java.lang.Float", "Float"});
        BASIC_TYPE_DEF.put("double", new String[]{"java.lang.Double", "Double"});

    }

    public FieldDef(String type, String name) {
        this("private", type, name);
    }

    public FieldDef(String visible, String type, String name) {
        this.visible = visible == null ? "private" : visible;
        this.name = name;
        processImportType(type);
    }

    /**
     * 类型处理
     * @param type
     */
    private void processImportType(String type) {
        int lastIndex = type.lastIndexOf(".");
        if (lastIndex > 0) {
            this.importType = type;
            this.type = type.substring(lastIndex+1);
        } else if (BASIC_TYPE_DEF.containsKey(type.toLowerCase())) {
            String[] typeDef = BASIC_TYPE_DEF.get(type.toLowerCase());
            this.importType = typeDef[0];
            this.type = typeDef[1];
        } else {
            throw new IllegalArgumentException("field type define error. [" + type + "] type not supported.");
        }
    }

    public String getGetterName() {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getSetterName() {
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
