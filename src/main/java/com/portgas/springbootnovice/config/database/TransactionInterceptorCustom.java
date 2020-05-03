package com.portgas.springbootnovice.config.database;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionInterceptorCustom implements Ordered {

    private int order;

    @Value("20")
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Around(value="@annotation(datasourceType)")
    public Object proceed(ProceedingJoinPoint pjp, DatasourceType datasourceType) throws Throwable {
        try {
            DBType oldDBType = DBContextHolder.getDbType();
            if( DBType.REPLICATE.equals(datasourceType.dbType())){
                DBContextHolder.setDbType(DBType.REPLICATE);
            }else{
                DBContextHolder.setDbType(DBType.MASTER);
            }
            Object result = pjp.proceed();
            DBContextHolder.setDbType(oldDBType);
            return result;
        } finally {
            // restore state
            DBContextHolder.clearDbType();
        }
    }
}
