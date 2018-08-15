package ${package};

<#list imports as import>
import ${import};
</#list>

public class ${prefix!""}${name}${suffix!""} <#if parent??>extends ${parent} </#if>{
<#list fields as field>
    ${field.visible} ${field.type} ${field.name};
</#list>

}
