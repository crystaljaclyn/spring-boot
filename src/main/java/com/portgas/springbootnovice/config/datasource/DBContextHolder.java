package com.portgas.springbootnovice.config.datasource;

public class DBContextHolder {
    private static final ThreadLocal<DBType> contextHolder = new ThreadLocal<>();

    public static void setDbType(DBType dbType) {
        if(dbType == null){
            throw new NullPointerException();
        }
        contextHolder.set(dbType);
    }

    public static DBType getDbType() {
        return (DBType) contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
