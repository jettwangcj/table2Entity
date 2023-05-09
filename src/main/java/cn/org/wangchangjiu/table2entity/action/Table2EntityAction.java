package cn.org.wangchangjiu.table2entity.action;

import cn.org.wangchangjiu.table2entity.ui.CreateTableCodeDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @Classname SQLConvertAction
 * @Description
 * @Date 2022/12/7 18:31
 * @Created by wangchangjiu
 */
public class Table2EntityAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        new CreateTableCodeDialog(anActionEvent).show();
    }
}
