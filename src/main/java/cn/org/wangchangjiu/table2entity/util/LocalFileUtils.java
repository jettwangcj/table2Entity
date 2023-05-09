package cn.org.wangchangjiu.table2entity.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;

import java.io.IOException;


public class LocalFileUtils {

    public static final String LINE = System.getProperty("line.separator");


    public static PsiDirectory getChildDirNotExistCreate(Project project, VirtualFile root, String childPackage) throws IOException {
        if (root == null || childPackage == null || childPackage == "") {

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


    public static PsiDirectory findParentDirectoryOrLevel(PsiDirectory file, String parentName, Integer level) {
        if (file == null || parentName == null) {
            return null;
        }
        VirtualFile root = ProjectRootManager.getInstance(file.getProject()).getFileIndex().getSourceRootForFile(file.getVirtualFile());
        PsiDirectory parent = file;
        PsiDirectory levelResult = file;
        int idx = 1;
        while (parent != null && !root.getPath().equals(parent.getVirtualFile().getPath())) {
            if (idx > level) {
                levelResult = levelResult.getParent();
            }
            if (parentName.equals(parent.getName())) {
                return parent;
            }
            parent = parent.getParent();
            idx++;
        }
        return levelResult;
    }

    public static String getPackageForDir(PsiDirectory dir) {
        VirtualFile contentRoot = ProjectRootManager.getInstance(dir.getProject()).getFileIndex().getSourceRootForFile(dir.getVirtualFile());
        String relativePath = dir.getVirtualFile().getPath().substring(contentRoot.getPath().length() + 1);
        return relativePath.replaceAll("/", ".");
    }
}
