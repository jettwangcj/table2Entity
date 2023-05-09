package cn.org.wangchangjiu.table2entity.ui;

import cn.org.wangchangjiu.table2entity.model.TableInfo;
import cn.org.wangchangjiu.table2entity.service.CreateTableParser;
import cn.org.wangchangjiu.table2entity.util.LocalFileUtils;
import cn.org.wangchangjiu.table2entity.util.VelocityUtils;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.codeStyle.CodeStyleManager;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CreateTableCodeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField packageField;
    private JButton packageBut;
    private JLabel packageLabel;
    private JLabel codeInputLabel;
    private JTextArea code;

    private String path;

    public CreateTableCodeDialog(AnActionEvent anActionEvent) {
        setContentPane(contentPane);
        setTitle("table2Entity");
        setSize(600, 500);
        setLocation(200, 200);
        setModal(true);
        setResizable(true);
        getRootPane().setDefaultButton(buttonOK);
        this.initEvent(anActionEvent.getProject());

        Project project = anActionEvent.getProject();

        buttonOK.addActionListener(e -> {

            String packageFieldText = packageField.getText();
            if (StringUtils.isEmpty(packageFieldText)) {
                return;
            }

            String createTableSQLText = code.getText();
            if (StringUtils.isEmpty(createTableSQLText)) {
                return;
            }

            TableInfo result = CreateTableParser.parser(createTableSQLText);
            String generateCode = VelocityUtils.generate(result, packageFieldText);

            VirtualFile thisVirtualFile = anActionEvent.getData(LangDataKeys.VIRTUAL_FILE);
            VirtualFile contentRoot = ProjectRootManager.getInstance(project).getFileIndex().getContentRootForFile(thisVirtualFile);

            WriteCommandAction.runWriteCommandAction(project, () -> {
                try {
                    PsiDirectory directory;
                    try {
                        directory = LocalFileUtils.getChildDirNotExistCreate(project,
                                contentRoot, "src.main.java." + packageFieldText);
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

    private void initEvent(Project project) {

        packageBut.addActionListener(actionEvent -> {

            PackageChooserDialog selector = new PackageChooserDialog("Select a Package", project);

            ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(
                    "MyToolbar", new DefaultActionGroup(new EmptyAction()), false);
            selector.getContentPane().add(toolbar.getComponent(), BorderLayout.NORTH);


            selector.show();
            PsiPackage selectedPackage = selector.getSelectedPackage();
            if (selectedPackage != null) {
                this.packageField.setText(selectedPackage.getQualifiedName());
            }

        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }


}
