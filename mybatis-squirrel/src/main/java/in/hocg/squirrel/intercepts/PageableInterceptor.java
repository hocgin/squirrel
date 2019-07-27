package in.hocg.squirrel.intercepts;

import in.hocg.squirrel.constant.StatementHandlerFields;
import in.hocg.squirrel.exception.SquirrelException;
import in.hocg.squirrel.page.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
import java.util.Map;
import java.util.Properties;

/**
 * Created by hocgin on 2019-07-25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
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
        
        Pageable pageable = getPageable(parameter);
        // todo 进行 SQL 拼接
        
        // todo 进行 排序?
        
        // todo 加入 分页?
        
        return invocation.proceed();
    }
    
    
    /**
     * 获取分页参数
     *
     * @param parameter
     * @return
     */
    private Pageable getPageable(Object parameter) {
        if (parameter instanceof Map) {
            Map<String, Object> parameterMap = (Map<String, Object>) parameter;
            for (String k : parameterMap.keySet()) {
                Object v = parameterMap.get(k);
                if (v instanceof Pageable) {
                    return (Pageable) v;
                }
            }
        }
        
        throw SquirrelException.wrap("请指定分页参数 {pageable} 并使用 @{param}", Pageable.class.getSimpleName(), Param.class.getSimpleName());
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
