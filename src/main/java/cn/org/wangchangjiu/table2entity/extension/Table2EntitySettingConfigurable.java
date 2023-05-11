package cn.org.wangchangjiu.table2entity.extension;

import cn.org.wangchangjiu.table2entity.model.ConfigSetting;
import cn.org.wangchangjiu.table2entity.ui.SettingConfigUI;
import com.intellij.ide.impl.ProjectUtil;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;

import javax.swing.*;

/**
 * @Classname Table2EntitySettingConfigurable
 * @Description
 * @Date 2023/5/11 15:10
 * @Created by wangchangjiu
 */
public class Table2EntitySettingConfigurable implements Configurable {

    private SettingConfigUI settingConfigUI;


    public Table2EntitySettingConfigurable(){
        this.settingConfigUI = new SettingConfigUI();
        Project activeProject = ProjectUtil.getActiveProject();
        Table2EntityConfigSettingCache state = Table2EntityConfigSettingCache.getInstance(activeProject);
        // 第一次打开配置页面，没有持久化数据，这个state为null
        if(state != null){
            ConfigSetting config = state.getConfig();
            if(config != null){
                this.settingConfigUI.getPackageField().setText(config.getPackagePath());
                this.settingConfigUI.getPathTextField().setText(config.getFullPath());
            }
        }

    }

    /**
     *  获取配置在 Settings/Preferences 中显示的名字
     * @return
     */
    @Override
    public String getDisplayName() {
        return "table2EntitySetting";
    }

    /**
     *  基于 Swing 设计我们配置界面的 UI
     * @return
     */
    @Override
    public JComponent createComponent() {
        return settingConfigUI.getSettingPanel();
    }

    /**
     *  提供给 IDE 判断配置是否发生变更，若返回 true，则配置界面中的 apply 按钮可点击
     * @return
     */
    @Override
    public boolean isModified() {
        return true;
    }

    /**
     *  当在配置页面点击 apply 或者 ok 按钮时，该方法会被调用
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {
        String fullPath = settingConfigUI.getPathTextField().getText();
        String packagePath = settingConfigUI.getPackageField().getText();
        if(fullPath == null || fullPath.length() == 0){
            //
            return;
        }
        Project activeProject = ProjectUtil.getActiveProject();
        Table2EntityConfigSettingCache state = Table2EntityConfigSettingCache.getInstance(activeProject);
        state.addConfig(new ConfigSetting(packagePath, fullPath));

    }
}
