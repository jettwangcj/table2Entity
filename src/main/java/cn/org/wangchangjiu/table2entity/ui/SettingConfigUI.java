package cn.org.wangchangjiu.table2entity.ui;

import com.intellij.ide.impl.ProjectUtil;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.psi.PsiPackage;

import javax.swing.*;

/**
 * @Classname SettingConfigUI
 * @Description
 * @Date 2023/5/11 15:22
 * @Created by wangchangjiu
 */
public class SettingConfigUI {
    private JLabel packageLabel;
    private JTextField packageField;
    private JButton packageBut;
    private JLabel pathLabel;
    private JTextField pathTextField;
    private JPanel settingPanel;

    public SettingConfigUI(){
        packageBut.addActionListener(actionEvent -> {

            PackageChooserDialog selector = new PackageChooserDialog("Select a Package", ProjectUtil.getActiveProject());
            selector.show();
            PsiPackage selectedPackage = selector.getSelectedPackage();
            if (selectedPackage != null) {
                this.packageField.setText(selectedPackage.getQualifiedName());
                String path = "src/main/java";
                if (selectedPackage.getQualifiedName() != null && selectedPackage.getQualifiedName().length() > 0) {
                    path += "/" + selectedPackage.getQualifiedName().replace(".", "/");
                }
                this.pathTextField.setText(path);
            }
        });
    }

    public JLabel getPackageLabel() {
        return packageLabel;
    }

    public JTextField getPackageField() {
        return packageField;
    }

    public JButton getPackageBut() {
        return packageBut;
    }

    public JLabel getPathLabel() {
        return pathLabel;
    }

    public JTextField getPathTextField() {
        return pathTextField;
    }

    public JPanel getSettingPanel() {
        return settingPanel;
    }
}
