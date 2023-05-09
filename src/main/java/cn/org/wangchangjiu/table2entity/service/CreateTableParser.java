package cn.org.wangchangjiu.table2entity.service;

import cn.org.wangchangjiu.table2entity.model.ColumnInfo;
import cn.org.wangchangjiu.table2entity.model.TableInfo;
import cn.org.wangchangjiu.table2entity.util.CommonUtil;
import cn.org.wangchangjiu.table2entity.util.TypeMappingEnum;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.NamedConstraint;

import java.io.StringReader;
import java.util.*;

/**
 * @Classname CreateTableParser
 * @Description
 * @Date 2023/5/6 17:55
 * @Created by wangchangjiu
 */
public class CreateTableParser {

    /**
     *  分析创建表语句
     * @param createTableSQLText
     * @return
     */
    public static TableInfo parser(String createTableSQLText) {
        TableInfo tableInfo = new TableInfo();
        Statement sqlStmt;
        try {
            sqlStmt = CCJSqlParserUtil.parse(new StringReader(createTableSQLText));

            if(!(sqlStmt instanceof CreateTable)){
                throw new RuntimeException("Illegal table creation SQL statement");
            }

            CreateTable createTable = CreateTable.class.cast(sqlStmt);

            if(createTable.getSelect() != null || createTable.getLikeTable() != null){
                throw new RuntimeException("create table syntax not support select or likeTable");
            }

            String tableName = CommonUtil.dropQuotationMark(createTable.getTable().getName());
            tableInfo.setEntityName(CommonUtil.underlineToHump(tableName, true));
            tableInfo.setAnnotations(Arrays.asList("@Data","@Entity","@Table(name = \""+ tableName +"\")"));

            Set<String> importPackages = CommonUtil.initEntityPackage();

            // 主键
            List<String> primaryKeys = new ArrayList<>();
            createTable.getIndexes().stream().filter(item -> {
                if(item instanceof NamedConstraint){
                    NamedConstraint namedConstraint = NamedConstraint.class.cast(item);
                    return "PRIMARY KEY".equalsIgnoreCase(namedConstraint.getType());
                }
                return false;
            }).forEach(item -> {
                NamedConstraint namedConstraint = NamedConstraint.class.cast(item);
                primaryKeys.addAll(namedConstraint.getColumnsNames());
            });

            List<ColumnDefinition> columnDefinitions = createTable.getColumnDefinitions();
            if(columnDefinitions != null && columnDefinitions.size() > 0){

                // 列信息
                List<ColumnInfo> columnInfos = getColumnInfos(importPackages, primaryKeys, columnDefinitions);
                tableInfo.setColumns(columnInfos);
            }

            tableInfo.setImportPackage(importPackages);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tableInfo;
    }

    /**
     *  分析列信息
     * @param importPackages
     * @param primaryKeys
     * @param columnDefinitions
     * @return
     */
    private static List<ColumnInfo> getColumnInfos(Set<String> importPackages, List<String> primaryKeys, List<ColumnDefinition> columnDefinitions) {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        columnDefinitions.stream().forEach(columnDefinition -> {
            ColumnInfo columnInfo = new ColumnInfo();

            // 列名
            String columnName = CommonUtil.dropQuotationMark(columnDefinition.getColumnName());
            columnInfo.setName(CommonUtil.underlineToHump(columnName, false));

            // 列注解
            List<String> annotations = new ArrayList<>();
            if(primaryKeys.contains(columnName)){
                // 主键
                annotations.add("@Id");
                // AUTO_INCREMENT
                Optional<String> optionalAutoIncrement = columnDefinition.getColumnSpecs().stream().filter(item -> "AUTO_INCREMENT".equalsIgnoreCase(item)).findFirst();
                if(optionalAutoIncrement.isPresent()){
                    annotations.add("@GeneratedValue(strategy = GenerationType.IDENTITY)");
                }
            }
            annotations.add("@Column(name = \"" + columnName + "\")");
            columnInfo.setAnnotations(annotations);

            // 列类型
            String dataType = columnDefinition.getColDataType().getDataType();
            TypeMappingEnum dbType = TypeMappingEnum.findByBbType(dataType);
            columnInfo.setType(dbType.getJavaType());
            importPackages.add("import " + dbType.getImportPck() + ";");

            // 列描述
            int commentIndex = columnDefinition.getColumnSpecs().indexOf("COMMENT");
            if(commentIndex < 0){
                commentIndex = columnDefinition.getColumnSpecs().indexOf("comment");
            }
            if(commentIndex > 0){
                List<String> commentDesc = columnDefinition.getColumnSpecs().subList(commentIndex + 1, commentIndex + 2);
                columnInfo.setDesc(commentDesc.get(0));
            }

            columnInfos.add(columnInfo);

        });
        return columnInfos;
    }
}
