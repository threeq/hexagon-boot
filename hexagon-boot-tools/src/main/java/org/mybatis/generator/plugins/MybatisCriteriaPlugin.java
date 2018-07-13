package org.mybatis.generator.plugins;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

public class MybatisCriteriaPlugin extends PluginAdapter
{
    private String databaseType;
    private boolean commonFile = false;

    public boolean validate(List<String> warnings) {
        this.databaseType = this.context.getJdbcConnectionConfiguration().getDriverClass();

        if (StringUtility.stringHasValue(this.properties.getProperty("commonFile")))
            this.commonFile = StringUtility.isTrue(this.properties.getProperty("commonFile"));
        else {
            this.commonFile = false;
        }
        return true;
    }

    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement Where_Clause = new XmlElement("sql");
        Where_Clause.addAttribute(new Attribute("id", "Example_Where_Clause"));
        StringBuilder sb = new StringBuilder();
        XmlElement dynamicElement = new XmlElement("trim");
        dynamicElement.addAttribute(new Attribute("prefix", "where"));
        dynamicElement.addAttribute(new Attribute("prefixOverrides", "and|or"));
        Where_Clause.addElement(dynamicElement);

        for (IntrospectedColumn introspectedColumn : introspectedTable.getNonPrimaryKeyColumns()) {
            XmlElement isNotNullElement = new XmlElement("if");
            String va = introspectedColumn.getJavaProperty("params.") + " != null";
            isNotNullElement.addAttribute(new Attribute("test", va));

            dynamicElement.addElement(isNotNullElement);
            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            sb.append(" = ");

            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "params."));

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        // 把主键也加入到条件中
        List<IntrospectedColumn> keysColumns = introspectedTable.getPrimaryKeyColumns();
        if(null != keysColumns && keysColumns.size() > 0) {
            IntrospectedColumn keys_introspectedColumn = keysColumns.get(0);
            XmlElement keyElement = new XmlElement("if");
            String keyElement_va = keys_introspectedColumn.getJavaProperty("params.") + " != null";
            keyElement.addAttribute(new Attribute("test", keyElement_va));
            dynamicElement.addElement(keyElement);

            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(keys_introspectedColumn));
            sb.append(" = ");

            sb.append(MyBatis3FormattingUtilities.getParameterClause(keys_introspectedColumn, "params."));

            keyElement.addElement(new TextElement(sb.toString()));


            // 新增根据多主键批量查询
            keyElement = new XmlElement("if");
            keyElement_va =  "params.ids != null";
            keyElement.addAttribute(new Attribute("test", keyElement_va));
            dynamicElement.addElement(keyElement);

            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(keys_introspectedColumn));
            sb.append(" in ");
            sb.append("\n\t\t");

            sb.append("<foreach item=\"item\" index=\"index\" collection=\"params.ids\" open=\"(\" separator=\",\" close=\")\">");
            sb.append("\n\t\t\t");
            sb.append("#{item}");
            sb.append("\n\t\t");
            sb.append("</foreach>");

            keyElement.addElement(new TextElement(sb.toString()));
        }

        document.getRootElement().getElements().add(2, Where_Clause);

        document.getRootElement().getElements().add(3, createSelectByParams(introspectedTable));
        document.getRootElement().getElements().add(4, createSelectCountByParams(introspectedTable));
        document.getRootElement().getElements().add(5, createSelectListByParams(introspectedTable));
        document.getRootElement().getElements().add(6, createInsertBatch(introspectedTable));

        return true;
    }

    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (this.databaseType.contains("oracle")) {
            XmlElement oracleHeadIncludeElement = new XmlElement("include");
            oracleHeadIncludeElement.addAttribute(new Attribute("refid", "common.Pagination_Head"));

            element.addElement(0, oracleHeadIncludeElement);

            XmlElement orderIncludeElement = new XmlElement("if");
            orderIncludeElement.addAttribute(new Attribute("test", "orderParam != null"));
            orderIncludeElement.addElement(new TextElement("order by ${orderParam}"));
            element.addElement(element.getElements().size(), orderIncludeElement);

            XmlElement oracleFootIncludeElement = new XmlElement("include");
            oracleFootIncludeElement.addAttribute(new Attribute("refid", "common.Pagination_Foot"));

            element.addElement(element.getElements().size(), oracleFootIncludeElement);
        } else if (this.databaseType.contains("mysql")) {
            XmlElement orderIncludeElement = new XmlElement("if");
            orderIncludeElement.addAttribute(new Attribute("test", "orderParam != null"));
            orderIncludeElement.addElement(new TextElement("order by ${orderParam}"));
            element.addElement(element.getElements().size(), orderIncludeElement);

            XmlElement mysqlLimitIncludeElement = new XmlElement("include");
            mysqlLimitIncludeElement.addAttribute(new Attribute("refid", "common.Pagination_Limit"));

            element.addElement(element.getElements().size(), mysqlLimitIncludeElement);
        }
        return true;
    }

    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        if (this.databaseType.contains("oracle")) {
            XmlElement oracleHeadIncludeElement = new XmlElement("include");
            oracleHeadIncludeElement.addAttribute(new Attribute("refid", "common.Pagination_Head"));

            element.addElement(0, oracleHeadIncludeElement);

            XmlElement orderIncludeElement = new XmlElement("if");
            orderIncludeElement.addAttribute(new Attribute("test", "orderParam != null"));
            orderIncludeElement.addElement(new TextElement("order by ${orderParam}"));
            element.addElement(element.getElements().size(), orderIncludeElement);

            XmlElement oracleFootIncludeElement = new XmlElement("include");
            oracleFootIncludeElement.addAttribute(new Attribute("refid", "common.Pagination_Foot"));

            element.addElement(element.getElements().size(), oracleFootIncludeElement);
        } else if (this.databaseType.contains("mysql")) {
            XmlElement orderIncludeElement = new XmlElement("if");
            orderIncludeElement.addAttribute(new Attribute("test", "orderParam != null"));
            orderIncludeElement.addElement(new TextElement("order by ${orderParam}"));
            element.addElement(element.getElements().size(), orderIncludeElement);

            XmlElement mysqlLimitIncludeElement = new XmlElement("include");
            mysqlLimitIncludeElement.addAttribute(new Attribute("refid", "common.Pagination_Limit"));

            element.addElement(element.getElements().size(), mysqlLimitIncludeElement);
        }
        return true;
    }

    private XmlElement createSelectByParams(IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();

        StringBuffer sb = new StringBuffer();

        XmlElement selectCriteria = new XmlElement("select");
        selectCriteria.addAttribute(new Attribute("id", "selectByParams"));
        selectCriteria.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        selectCriteria.addElement(new TextElement("select"));

        XmlElement baseColumnIncludeElement = new XmlElement("include");
        baseColumnIncludeElement.addAttribute(new Attribute("refid", "Base_Column_List"));
        selectCriteria.addElement(baseColumnIncludeElement);

        sb.append("from ").append(tableName);
        selectCriteria.addElement(new TextElement(sb.toString()));

        XmlElement oracleWhereIncludeElement = new XmlElement("include");
        oracleWhereIncludeElement.addAttribute(new Attribute("refid", "Example_Where_Clause"));
        selectCriteria.addElement(oracleWhereIncludeElement);

        return selectCriteria;
    }

    private XmlElement createSelectCountByParams(IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();

        StringBuffer sb = new StringBuffer();

        XmlElement selectCriteria = new XmlElement("select");
        selectCriteria.addAttribute(new Attribute("id", "selectCountByParams"));
        selectCriteria.addAttribute(new Attribute("resultType", "int"));

        sb.append("select").append(" count(*)").append(" from ").append(tableName);
        selectCriteria.addElement(new TextElement(sb.toString()));

        XmlElement oracleWhereIncludeElement = new XmlElement("include");
        oracleWhereIncludeElement.addAttribute(new Attribute("refid", "Example_Where_Clause"));
        selectCriteria.addElement(oracleWhereIncludeElement);

        return selectCriteria;
    }

    private XmlElement createSelectListByParams(IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();

        StringBuffer sb = new StringBuffer();

        XmlElement selectCriteria = new XmlElement("select");
        selectCriteria.addAttribute(new Attribute("id", "selectListByParams"));
        selectCriteria.addAttribute(new Attribute("resultType", "list"));
        selectCriteria.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        if (this.databaseType.contains("oracle")) {
            XmlElement oracleHeadIncludeElement = new XmlElement("include");
            oracleHeadIncludeElement.addAttribute(new Attribute("refid", "common.Pagination_Head"));
            selectCriteria.addElement(oracleHeadIncludeElement);

            selectCriteria.addElement(new TextElement("select"));
            XmlElement baseColumnIncludeElement = new XmlElement("include");
            baseColumnIncludeElement.addAttribute(new Attribute("refid", "Base_Column_List"));
            selectCriteria.addElement(baseColumnIncludeElement);

            sb.append("from ").append(tableName);
            selectCriteria.addElement(new TextElement(sb.toString()));

            XmlElement oracleWhereIncludeElement = new XmlElement("include");
            oracleWhereIncludeElement.addAttribute(new Attribute("refid", "Example_Where_Clause"));
            selectCriteria.addElement(oracleWhereIncludeElement);

            XmlElement orderIncludeElement = new XmlElement("if");
            orderIncludeElement.addAttribute(new Attribute("test", "orderParam != null"));
            orderIncludeElement.addElement(new TextElement("order by ${orderParam}"));
            selectCriteria.addElement(orderIncludeElement);

            XmlElement oracleFootIncludeElement = new XmlElement("include");
            oracleFootIncludeElement.addAttribute(new Attribute("refid", "common.Pagination_Foot"));
            selectCriteria.addElement(oracleFootIncludeElement);
        } else if (this.databaseType.contains("mysql")) {
            selectCriteria.addElement(new TextElement("select"));
            XmlElement baseColumnIncludeElement = new XmlElement("include");
            baseColumnIncludeElement.addAttribute(new Attribute("refid", "Base_Column_List"));
            selectCriteria.addElement(baseColumnIncludeElement);

            sb.append("from ").append(tableName);
            selectCriteria.addElement(new TextElement(sb.toString()));

            XmlElement oracleWhereIncludeElement = new XmlElement("include");
            oracleWhereIncludeElement.addAttribute(new Attribute("refid", "Example_Where_Clause"));
            selectCriteria.addElement(oracleWhereIncludeElement);

            XmlElement orderIncludeElement = new XmlElement("if");
            orderIncludeElement.addAttribute(new Attribute("test", "orderParam != null"));
            orderIncludeElement.addElement(new TextElement("order by ${orderParam}"));
            selectCriteria.addElement(orderIncludeElement);

            XmlElement mysqlLimitIncludeElement = new XmlElement("include");
            mysqlLimitIncludeElement.addAttribute(new Attribute("refid", "common.Pagination_Limit"));
            selectCriteria.addElement(mysqlLimitIncludeElement);
        }
        return selectCriteria;
    }

    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable)
    {
        if (!this.commonFile) {
            Document document = new Document("-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
            XmlElement answer = new XmlElement("mapper");
            document.setRootElement(answer);
            answer.addAttribute(
                    new Attribute("namespace",
                            "common"));

            if (this.databaseType.contains("oracle")) {
                answer.addElement(getOracleHead());
                answer.addElement(getOracleFoot());
            } else if (this.databaseType.contains("mysql")) {
                answer.addElement(getMysqlLimit());
            }

            GeneratedXmlFile gxf = new GeneratedXmlFile(document, this.properties.getProperty("fileName", "CommonMapper.xml"),
                    this.context.getSqlMapGeneratorConfiguration().getTargetPackage(),
                    this.context.getSqlMapGeneratorConfiguration().getTargetProject(),
                    false, context.getXmlFormatter());

            List<GeneratedXmlFile> files = new ArrayList<GeneratedXmlFile>(1);
            files.add(gxf);
            this.commonFile = true;
            return files;
        }

        return null;
    }

    private XmlElement getOracleHead() {
        XmlElement answer = new XmlElement("sql");

        answer.addAttribute(new Attribute("id", "Pagination_Head"));

        XmlElement outerisNotEmptyElement = new XmlElement("if");
        outerisNotEmptyElement.addAttribute(new Attribute("test", "startIndex != null and endIndex != null"));
        outerisNotEmptyElement.addElement(new TextElement("<![CDATA[ select * from ( select row_.*, rownum rn from ( ]]>"));
        answer.addElement(outerisNotEmptyElement);
        return answer;
    }

    private XmlElement getOracleFoot() {
        XmlElement answer = new XmlElement("sql");

        answer.addAttribute(new Attribute("id", "Pagination_Foot"));

        XmlElement outerisNotEmptyElement = new XmlElement("if");
        outerisNotEmptyElement.addAttribute(new Attribute("test", "startIndex != null and endIndex != null"));
        outerisNotEmptyElement.addElement(
                new TextElement("<![CDATA[ ) row_ where rownum <= #{endIndex} ) where rn > #{startIndex} ]]>"));
        answer.addElement(outerisNotEmptyElement);
        return answer;
    }

    private XmlElement getMysqlLimit() {
        XmlElement answer = new XmlElement("sql");

        answer.addAttribute(new Attribute("id", "Pagination_Limit"));

        XmlElement outerisNotEmptyElement = new XmlElement("if");
        outerisNotEmptyElement.addAttribute(new Attribute("test", "pageOffset != null and pageSize != null"));
        outerisNotEmptyElement.addElement(new TextElement("<![CDATA[ limit #{pageOffset} , #{pageSize} ]]>"));
        answer.addElement(outerisNotEmptyElement);
        return answer;
    }


    private XmlElement createInsertBatch(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("insert");

        answer.addAttribute(new Attribute("id", "insertBatch"));
        // 参数为List
        answer.addAttribute(new Attribute("parameterType","java.util.List"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into "); //$NON-NLS-1$
        insertClause.append(introspectedTable
                .getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" ("); //$NON-NLS-1$

        valuesClause.append("values ");
        valuesClause.append("\n\t");
        valuesClause.append("<foreach collection=\"list\" item=\"obj\" separator=\",\">");
        valuesClause.append("\n\t");
        valuesClause.append(" (");

        List<String> valuesClauses = new ArrayList<String>();
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        for (int i = 0; i < columns.size(); i++) {
            IntrospectedColumn introspectedColumn = columns.get(i);
            if (introspectedColumn.isIdentity()) {
                // cannot set values on identity fields
                continue;
            }

            insertClause.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "obj."));
            if (i + 1 < columns.size()) {
                if (!columns.get(i + 1).isIdentity()) {
                    insertClause.append(", "); //$NON-NLS-1$
                    valuesClause.append(", "); //$NON-NLS-1$
                }
            }

            if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }

        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(')').append("\n\t").append("</foreach>");
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }

        return answer;

    }

    private XmlElement createSelectListByKeys(IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();

        StringBuffer sb = new StringBuffer();

        XmlElement selectCriteria = new XmlElement("select");
        selectCriteria.addAttribute(new Attribute("id", "selectListByKeys"));
        selectCriteria.addAttribute(new Attribute("resultType", "list"));
        selectCriteria.addAttribute(new Attribute("resultMap", "BaseResultMap"));

        selectCriteria.addElement(new TextElement("select"));
        XmlElement baseColumnIncludeElement = new XmlElement("include");
        baseColumnIncludeElement.addAttribute(new Attribute("refid", "Base_Column_List"));
        selectCriteria.addElement(baseColumnIncludeElement);

        sb.append("from ").append(tableName);
        selectCriteria.addElement(new TextElement(sb.toString()));

        XmlElement oracleWhereIncludeElement = new XmlElement("include");
        oracleWhereIncludeElement.addAttribute(new Attribute("refid", "Example_Where_Clause"));
        selectCriteria.addElement(oracleWhereIncludeElement);

        XmlElement orderIncludeElement = new XmlElement("if");
        orderIncludeElement.addAttribute(new Attribute("test", "orderParam != null"));
        orderIncludeElement.addElement(new TextElement("order by ${orderParam}"));
        selectCriteria.addElement(orderIncludeElement);

        return selectCriteria;
    }
}