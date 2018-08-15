package ${package};

<#list imports as import>
import ${import};
</#list>

public interface ${prefix!""}${name}${suffix!""} <#if parent??>extends ${parent}${"<"}${name}${">"} </#if> {

}
