package com.hexagon.boot.generator.def;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EntityDef extends ClassDef{
    private boolean useBuilder;
    private boolean useLombok;

    public EntityDef(String _package, String name, String parent, boolean useLombok) {
        super(_package, name, parent);
        this.useLombok = useLombok;
        if(this.useLombok) {
            this.getImports().add("lombok.Getter");
            this.getImports().add("lombok.Setter");
        }
        if(this.useBuilder) {
            this.getImports().add("lombok.Builder");
        }
    }

    public EntityDef(String _package, String name, String parent) {
        this(_package, name, parent, false);
    }
}
