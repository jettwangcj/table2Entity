package cn.org.wangchangjiu.table2entity.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname ColumnInfo
 * @Description
 * @Date 2023/5/8 11:11
 * @Created by wangchangjiu
 */
public class ColumnInfo implements Serializable {

    /**
     *  注解
     */
    private List<String> annotations;

    /**
     *  列描述
     */
    private String desc;

    /**
     *  列类型
     */
    private String type;

    /**
     *  列名字
     */
    private String name;

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
