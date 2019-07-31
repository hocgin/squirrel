package in.hocg.squirrel.devtools.sql;

import com.google.common.base.Stopwatch;
import in.hocg.squirrel.constant.StatementHandlerFields;
import in.hocg.squirrel.intercepts.AbstractInterceptor;
import in.hocg.squirrel.utils.Pretty;
import in.hocg.squirrel.utils.TextFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Created by hocgin on 2019-07-28.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {
                Statement.class, ResultHandler.class
        }),
        @Signature(type = StatementHandler.class, method = "update", args = {
                Statement.class
        }),
        @Signature(type = StatementHandler.class, method = "batch", args = {
                Statement.class
        })
})
public class WatchSqlInterceptor extends AbstractInterceptor {
    
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        PreparedStatement statement = getStatement(args[0]);
        
        
        Stopwatch stopwatch = Stopwatch.createStarted();
        Object result = invocation.proceed();
        stopwatch.stop();
    
        StatementHandler target = getTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(StatementHandlerFields.DELEGATE__MAPPED_STATEMENT);
        BoundSql boundSql = target.getBoundSql();
        String originalSql = Pretty.sql(boundSql);
        Configuration configuration = mappedStatement.getConfiguration();
    
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        List<Object> sqlArgs = new ArrayList<>();
        if (parameterMappings != null) {
            MetaObject parameterMetaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    String propertyName = parameterMapping.getProperty();
                    Object value;
                    if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else {
                        value = parameterMetaObject == null ? null : parameterMetaObject.getValue(propertyName);
                    }
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    // .. 处理参数设值
                    if (value instanceof String) {
                        sqlArgs.add(String.format("'%s'", value));
                    } else {
                        sqlArgs.add(Objects.toString(value));
                    }
                    log.debug("{}: {}({})", i, value, jdbcType);
                }
            }
        }
    
        String sql = TextFormatter.arrayFormat(originalSql, sqlArgs.toArray(), TextFormatter.SQL_PLACEHOLDER);
    
        StringJoiner stringJoiner = new StringJoiner(System.lineSeparator())
                .add("")
                .add("========================================================================================")
                .add("Statement Id: " + mappedStatement.getId())
                .add("SQL: " + sql)
                .add("执行耗时: " + stopwatch.toString())
                .add("========================================================================================");
        log.debug(stringJoiner.toString());
        return result;
    }
    
    
    private <T> T getStatement(Object statement) {
        if (Proxy.isProxyClass(statement.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(statement);
            return getTarget(metaObject.getValue("h.statement"));
        }
        return (T) statement;
    }
}
