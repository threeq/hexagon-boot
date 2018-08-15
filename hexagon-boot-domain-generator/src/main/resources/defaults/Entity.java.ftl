package ${package};

<#list imports as import>
import ${import};
</#list>

<#if useLombok>
@Getter
@Setter
</#if>
<#if useBuilder>
@Builder
</#if>
public class ${name} <#if parent??>extends ${parent} </#if>{
<#list fields as field>



    ${field.visible} ${field.type} ${field.name};
</#list>

<#if !useLombok>
<#list fields as field>
    public ${field.type} ${field.getterName}() {
        return ${field.name};
    }

    public void ${field.setterName}(${field.type} ${field.name}) {
        this.${field.name} = ${field.name};
    }

</#list>
</#if>






}
