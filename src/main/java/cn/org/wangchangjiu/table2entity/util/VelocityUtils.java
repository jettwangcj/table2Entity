package cn.org.wangchangjiu.table2entity.util;

import cn.org.wangchangjiu.table2entity.model.TableInfo;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.Date;

/**
 * @Classname VelocityUtils
 * @Description
 * @Date 2023/5/8 17:00
 * @Created by wangchangjiu
 */
public class VelocityUtils {

    public static String generate(TableInfo tableInfo, String packagePath) {

        final ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(VelocityUtils.class.getClassLoader());

        //实例化
        VelocityEngine velocityEngine = new VelocityEngine();

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableInfo", tableInfo);
        velocityContext.put("packagePath", packagePath);
        velocityContext.put("date", MyDateUtils.dateToString(new Date()));

        //关闭日志
        velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();

        StringWriter writer = new StringWriter();
        //生成代码
        velocityEngine.getTemplate("template/entity.java.vm").merge(velocityContext, writer);

        String code = writer.toString();

       /* StringBuilder sb = new StringBuilder(code);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
*/
        Thread.currentThread().setContextClassLoader(oldContextClassLoader);
        return code.replaceAll("\r\n", "\n");
    }

}
