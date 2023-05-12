package cn.org.wangchangjiu.table2entity.ui;

import cn.org.wangchangjiu.table2entity.action.OpenPathConfigSettingAction;
import cn.org.wangchangjiu.table2entity.extension.Table2EntityConfigSettingCache;
import cn.org.wangchangjiu.table2entity.model.ConfigSetting;
import cn.org.wangchangjiu.table2entity.model.TableInfo;
import cn.org.wangchangjiu.table2entity.service.CreateTableParser;
import cn.org.wangchangjiu.table2entity.util.CommonUtil;
import cn.org.wangchangjiu.table2entity.util.LocalFileUtils;
import cn.org.wangchangjiu.table2entity.util.VelocityUtils;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.ui.GotItTooltip;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.IOException;

public class CreateTableCodeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel codeInputLabel;
    private JTextArea code;


    public CreateTableCodeDialog(AnActionEvent anActionEvent) {
        setContentPane(contentPane);
        setTitle("table2Entity");
        setSize(600, 500);
        setLocation(200, 200);
        setModal(true);
        setResizable(true);
        getRootPane().setDefaultButton(buttonOK);
        Project project = anActionEvent.getProject();

        // 监听事件
        buttonOK.addActionListener(e -> {

            Table2EntityConfigSettingCache state = Table2EntityConfigSettingCache.getInstance(anActionEvent.getProject());
            if(state == null || state.getConfig() == null || StringUtils.isEmpty(state.getConfig().getFullPath())){

                Notification notification = new Notification("Print", "table2EntitySetting", "检查到没有设置代码生成路径", NotificationType.INFORMATION);
                // 在提示消息中，增加一个 Action，可以通过 Action 一步打开配置界面
                notification.addAction(new OpenPathConfigSettingAction("打开配置界面"));
                Notifications.Bus.notify(notification, project);
                return;

            }

            ConfigSetting config = state.getConfig();

            // 包路径
            String packageFieldText = config.getPackagePath();
            // 包路径 增加了 src.main.java 后面创建java文件需要这个路径
            String fullPck = CommonUtil.pathToPackage(state.getConfig().getFullPath());

            if (StringUtils.isEmpty(packageFieldText)) {
                return;
            }

            String createTableSQLText = code.getText();
            if (StringUtils.isEmpty(createTableSQLText)) {
                new GotItTooltip("got.it.id", "", ProjectManager.getInstance().getDefaultProject()).
                        withShowCount(Integer.MAX_VALUE).
                        withHeader("请正确输入MySQL建表语句!").
                        show(codeInputLabel, GotItTooltip.RIGHT_MIDDLE);
                return;
            }

            TableInfo result;
            try {
                result = CreateTableParser.parser(createTableSQLText);
            }catch (Exception ex){
                Messages.showMessageDialog(ex.getMessage(), "error massage:", Messages.getErrorIcon());
                return;
            }

            String generateCode = VelocityUtils.generate(result, packageFieldText);

            VirtualFile thisVirtualFile = anActionEvent.getData(LangDataKeys.VIRTUAL_FILE);
            VirtualFile contentRoot = ProjectRootManager.getInstance(project).getFileIndex().getContentRootForFile(thisVirtualFile);

            WriteCommandAction.runWriteCommandAction(project, () -> {
                try {
                    PsiDirectory directory;
                    try {
                        directory = LocalFileUtils.getChildDirNotExistCreate(project,
                                contentRoot, fullPck);
                    } catch (IOException ex) {
                        throw new RuntimeException("find or create directory error: ", ex);
                    }
                    PsiFile generateEntityFile = PsiFileFactory.getInstance(project)
                            .createFileFromText(result.getEntityName() + ".java", JavaFileType.INSTANCE, generateCode);

                    //格式化代码
                    CodeStyleManager.getInstance(project).reformat(generateEntityFile);

                    directory.add(generateEntityFile);

                } catch (Exception ex) {
                    // 弹框提示
                    Messages.showMessageDialog(ex.getMessage(), "error massage:", Messages.getErrorIcon());
                }
            });
            onOK();
        });
    }


    private void onOK() {
        // add your code here
        dispose();
    }


}
