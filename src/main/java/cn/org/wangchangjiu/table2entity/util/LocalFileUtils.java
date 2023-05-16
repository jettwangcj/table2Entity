package cn.org.wangchangjiu.table2entity.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;

import java.io.IOException;


public class LocalFileUtils {

    public static PsiDirectory getChildDirNotExistCreate(Project project, VirtualFile root, String childPackage) throws IOException {
        if (root == null || childPackage == null || childPackage == "") {
            return null;
        }
        String[] childs = childPackage.split("\\.");
        VirtualFile childFile = root;
        for (String child : childs) {
            VirtualFile existChildFile = childFile.findChild(child);
            if (existChildFile != null) {
                childFile = existChildFile;
            } else {
                childFile = childFile.createChildDirectory(null, child);
            }
        }

        return PsiManager.getInstance(project).findDirectory(childFile);
    }
}
