package cn.org.wangchangjiu.table2entity.util;

/**
 * @Classname TypeMappingEnum
 * @Description
 * @Date 2023/5/8 11:49
 * @Created by wangchangjiu
 */
public enum TypeMappingEnum {

    JDBC_STRING("varchar|char|tinytext|mediumtext|longtext","String", "java.lang.String"),

    JDBC_DATE("date|datetime|timestamp","Date", "java.util.Date"),

    JDBC_LONG("int8|bigint","Long", "java.lang.Long"),

    JDBC_BOOLEAN("boolean","Boolean", "java.lang.Boolean"),

    DECIMAL_DOUBLE("decimal","Double", "java.lang.Double"),

    JDBC_INTEGER("integer|tinyint|smallint|mediumint|int4","Integer", "java.lang.Integer"),

    TIME_LOCALTIME("time","LocalTime", "java.time.LocalTime");


    private String dbType;
    private String javaType;
    private String importPck;

    TypeMappingEnum(String dbType, String javaType, String importPck) {
        this.dbType = dbType;
        this.javaType = javaType;
        this.importPck = importPck;
    }

    public static TypeMappingEnum findByBbType(String dbType){
        for(TypeMappingEnum typeMappingEnum: TypeMappingEnum.values()){
            if(typeMappingEnum.getDbType().contains(dbType)){
                return typeMappingEnum;
            }
        }
        return null;
    }

    public String getDbType() {
        return dbType;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getImportPck() {
        return importPck;
    }
}
