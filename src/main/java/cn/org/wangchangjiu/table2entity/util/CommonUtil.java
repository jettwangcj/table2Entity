package cn.org.wangchangjiu.table2entity.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname CommonUtil
 * @Description
 * @Date 2023/5/8 14:15
 * @Created by wangchangjiu
 */
public class CommonUtil {

    private static Pattern UNDERLINE_PATTERN = Pattern.compile("_([a-z])");

    private static Pattern QUOTATION_MARK_PATTERN = Pattern.compile("\\`(.*?)\\`");

    public static String dropQuotationMark(String name){
        if(name == null || name.length() == 0){
            return name;
        }
        Matcher matcher = QUOTATION_MARK_PATTERN.matcher(name);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return name;
    }

    public static String underlineToHump (String str, boolean titleCase){
        if(str == null || str.length() == 0){
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = UNDERLINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(0).toUpperCase());
        }
        matcher.appendTail(sb);
        if(titleCase){
            sb.setCharAt(0, (char) ((int)sb.charAt(0) - 32));
        }
        return sb.toString().replaceAll("_", "");
    }

    public static Set<String> initEntityPackage(){
        Set<String> importPackages = new HashSet<>();
        importPackages.add("import lombok.Data;");
        importPackages.add("import javax.persistence.*;");
        return importPackages;
    }

    public static void main(String[] args) {
       System.out.println( underlineToHump("too", false));
    }

}
