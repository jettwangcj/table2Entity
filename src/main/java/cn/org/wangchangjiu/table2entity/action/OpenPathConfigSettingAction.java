package cn.org.wangchangjiu.table2entity.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @Classname OpenPathConfigSettingAction
 * @Description
 * @Date 2023/5/11 19:40
 * @Created by wangchangjiu
 */
public class OpenPathConfigSettingAction extends NotificationAction {

    public OpenPathConfigSettingAction(String text) {
        super(text);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification notification) {
        // IntelliJ SDK 提供的一个工具类，可以通过配置项名字，直接显示对应的配置界面
        ShowSettingsUtil.getInstance().showSettingsDialog(e.getProject(), "table2EntitySetting");
        notification.expire();
    }
}
