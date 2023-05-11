package cn.org.wangchangjiu.table2entity.listener;

import cn.org.wangchangjiu.table2entity.action.OpenPathConfigSettingAction;
import cn.org.wangchangjiu.table2entity.extension.Table2EntityConfigSettingCache;
import cn.org.wangchangjiu.table2entity.model.ConfigSetting;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

/**
 * @Classname PathConfigSettingListener
 * @Description
 * @Date 2023/5/11 18:35
 * @Created by wangchangjiu
 */
public class PathConfigSettingListener implements ProjectManagerListener {

    /**
     *  项目监听器 项目打开完成时触发
     * @param project opening project
     */
    @Override
    public void projectOpened(@NotNull Project project) {
        Table2EntityConfigSettingCache instance = Table2EntityConfigSettingCache.getInstance(project);
        ConfigSetting config = instance.getConfig();
        if(config == null){
            Notification notification = new Notification("Print", "table2EntitySetting", "检查到没有设置代码生成路径", NotificationType.INFORMATION);
            // 在提示消息中，增加一个 Action，可以通过 Action 一步打开配置界面
            notification.addAction(new OpenPathConfigSettingAction("打开配置界面"));
            Notifications.Bus.notify(notification, project);
        }
    }


}
