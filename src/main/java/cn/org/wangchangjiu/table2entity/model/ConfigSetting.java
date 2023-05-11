package cn.org.wangchangjiu.table2entity.model;

import java.io.Serializable;

/**
 * @Classname ConfigSetting
 * @Description
 * @Date 2023/5/11 16:40
 * @Created by wangchangjiu
 */
public class ConfigSetting implements Serializable {

    /**
     *  包路径
     */
    private String packagePath;

    /**
     *  包括 src/main/java 的包路径
     */
    private String fullPath;

    public ConfigSetting(){}

    public ConfigSetting(String packagePath, String fullPath){
        this.packagePath = packagePath;
        this.fullPath = fullPath;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
