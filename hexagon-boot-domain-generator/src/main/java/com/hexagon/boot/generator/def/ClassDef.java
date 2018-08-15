package com.hexagon.boot.generator.def;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassDef {
    private String outputPath;
    private String _package;
    private String name;
    private String prefix;
    private String suffix;
    private String parent;
    private SortedSet<String> imports;
    private List<FieldDef> fields;
    private Map<String, Map<String, Object>> ctx;

    public ClassDef(String _package, String name, String parent) {
        this._package = _package;
        this.name = name;
        this.imports = new TreeSet<>();
        this.fields = new ArrayList<>();

        processParent(parent);
    }

    public ClassDef(String _package, String name) {
        this(_package, name, null);
    }

    private void processParent(String parent) {
        if(parent==null||"".equals(parent)) {
            return;
        }
        int lastIndex = parent.lastIndexOf(".");
        if(lastIndex>0) {
            this.imports.add(parent);
            this.parent = parent.substring(lastIndex+1);
        } else {
            this.parent = parent;
        }
    }

    public ClassDef addField(FieldDef ... fieldDefs) {
        this.fields.addAll(Arrays.asList(fieldDefs));
        this.imports.addAll(Stream.of(fieldDefs).map(FieldDef::getImportType).collect(Collectors.toList()));
        return this;
    }

    public ClassDef addImport(String ... imports) {
        this.imports.addAll(Arrays.asList(imports));
        return this;
    }

    public List<FieldDef> getFields() {
        return fields;
    }

    public Set<String> getImports() {
        return imports;
    }

    public String getPackage() {
        return _package;
    }

    public void setPackage(String _package) {
        this._package = _package;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setCtx(Map<String, Map<String,Object>> ctx) {
        this.ctx = ctx;
    }

    public Map<String, Map<String,Object>> getCtx() {
        return ctx;
    }
    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
