package cn.org.wangchangjiu.table2entity.extension;

import cn.org.wangchangjiu.table2entity.model.ConfigSetting;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@State(name = "configSetting.cache", storages = {@Storage(value = "configSetting-cache.xml")})
public class Table2EntityConfigSettingCache implements PersistentStateComponent<Table2EntityConfigSettingCache> {

    private ConfigSetting configSetting;

    public static Table2EntityConfigSettingCache getInstance(Project project) {
        if(project == null){
            return null;
        }
        return project.getService(Table2EntityConfigSettingCache.class);
    }

    public void addConfig(ConfigSetting configSetting){
        this.configSetting = configSetting;
    }

    public ConfigSetting getConfig(){
       return this.configSetting;
    }


    /**
     * getState() 方法在每次修改数据被保存时都会调用，
     * 该方法返回配置对象，以 XML 协议序列化后存储到文件中
     * @return
     */
    @Override
    public @Nullable Table2EntityConfigSettingCache getState() {
        return this;
    }

    /**
     *  IDE 启动时，会加载已安装的插件，实现的 loadState() 方法，在插件被加载时会被调用，IDE 将数据反序列化为对象，作为方法的入参数
     * @param state loaded component state
     */
    @Override
    public void loadState(@NotNull Table2EntityConfigSettingCache state) {
        if (state.configSetting == null) {
            return;
        }
        this.configSetting = state.configSetting;
    }
}
