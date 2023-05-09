package cn.org.wangchangjiu.table2entity.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Classname TableParserResult
 * @Description
 * @Date 2023/5/6 17:56
 * @Created by wangchangjiu
 */
public class TableInfo implements Serializable {

    /**
     *  实体名称
     */
    private String entityName;

    /**
     *  表描述
     */
    private String desc;

    /**
     *  实体注解
     */
    private List<String> annotations;

    /**
     *  导入的包
     */
    private Set<String> importPackage;

    /**
     *  列信息
     */
    private List<ColumnInfo> columns;

    public List<String> getAnnotations() {
        return annotations;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Set<String> getImportPackage() {
        return importPackage;
    }

    public void setImportPackage(Set<String> importPackage) {
        this.importPackage = importPackage;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }
}
