package org.mybatis.generator.plugins;


import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ExtendsInterfacePlugin extends PluginAdapter
{
    private String extendInterface;
    /**
     * {@inheritDoc}
     */
    public boolean validate(List<String> warnings) {
        this.extendInterface = this.properties.getProperty("extendInterface");
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientGenerated(Interface interfaze,
                                   TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        if (extendInterface != null) {
            FullyQualifiedJavaType rootInterface = new FullyQualifiedJavaType(extendInterface);
            FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
            rootInterface.addTypeArgument(type);

            interfaze.addSuperInterface(rootInterface);
            interfaze.addImportedType(rootInterface);

            //删除原有函数,保留blob类型的函数
            List<Method> allList = interfaze.getMethods();
            List<Method> mList = new ArrayList<Method>();
            for (Method method :allList){
                if (((method.getName()).indexOf("BLOB"))!=-1){
                    mList.add(method);
                }
            }
            interfaze.getMethods().clear();
            interfaze.getMethods().addAll(mList);

            // add by damao   mapper接口中加入import
            Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
            FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(
                    introspectedTable.getBaseRecordType());
            importedTypes.add(parameterType);
            interfaze.addImportedTypes(importedTypes);
        }
        return true;
    }

}