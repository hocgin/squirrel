package in.hocg.squirrel.intercepts;

import in.hocg.squirrel.constant.StatementHandlerFields;
import in.hocg.squirrel.page.Pageable;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by hocgin on 2019-07-25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class PageableInterceptor extends AbstractInterceptor {
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = getTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(handler);
        MappedStatement statement = (MappedStatement) metaObject.getValue(StatementHandlerFields.DELEGATE__MAPPED_STATEMENT);
        
        if (!SqlCommandType.SELECT.equals(statement.getSqlCommandType())
                || StatementType.CALLABLE.equals(statement.getStatementType())) {
            return invocation.proceed();
        }
        
        BoundSql boundSql = (BoundSql) metaObject.getValue(StatementHandlerFields.DELEGATE__BOUND_SQL);
        Object parameter = boundSql.getParameterObject();
        
        
        Pageable pageable = null;
        if (parameter instanceof Pageable) {
            pageable = (Pageable) parameter;
        } else {
            return invocation.proceed();
        }
    
        // todo
    
    
        return invocation.proceed();
    }
    
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }
    
    @Override
    public void setProperties(Properties properties) {
        System.out.println();
    }
}
