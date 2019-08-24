package in.hocg.squirrel.intercepts.pageable;

import in.hocg.squirrel.constant.BoundSqlFields;
import in.hocg.squirrel.core.Dialect;
import in.hocg.squirrel.intercepts.AbstractInterceptor;
import in.hocg.squirrel.intercepts.pageable.builder.PageableBuilder;
import in.hocg.squirrel.intercepts.pageable.builder.PageableBuilderFactory;
import in.hocg.squirrel.page.Page;
import in.hocg.squirrel.utils.JdbcUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by hocgin on 2019-07-25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class
        }),
        @Signature(type = Executor.class, method = "query", args = {
                MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class
        })
})
public class PageableInterceptor extends AbstractInterceptor {
    
    private Dialect dialect;
    
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameter = args[1];
        Optional<Page<?>> optional = getPageable(parameter);
        
        if (StatementType.CALLABLE.equals(statement.getStatementType())
                || !optional.isPresent()) {
            return invocation.proceed();
        }
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        Executor executor = getTarget(invocation.getTarget());
        BoundSql boundSql;
        CacheKey cacheKey;
        if (args.length == 4) {
            boundSql = statement.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(statement, parameter, rowBounds, boundSql);
        } else {
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }
        
        // 设置方言类型
        if (Objects.isNull(dialect)) {
            this.dialect = getDialect(executor.getTransaction().getConnection());
        }
        PageableBuilder builder = PageableBuilderFactory.getPageableBuilder(dialect);
    
        Page<?> pageable = optional.get();
        
        // 如果不需要获取总数
        if (pageable.isSearchCount()) {
            long total = getTotal(builder, statement, boundSql, parameter,
                    rowBounds, resultHandler, executor);
            pageable.setTotal(total);
        }
        
        // 获取数据
        List records = getRecords(builder, statement, boundSql, cacheKey, parameter,
                rowBounds, resultHandler, executor, pageable);
        pageable.setRecords(records);
        
        return Collections.unmodifiableList(Collections.singletonList(pageable));
    }
    
    /**
     * 获取方言类型
     *
     * @param connection
     * @return
     */
    private Dialect getDialect(Connection connection) {
        try {
            String url = connection.getMetaData().getURL();
            return JdbcUtility.getDialect(url);
        } catch (SQLException e) {
            return Dialect.Unknown;
        }
    }
    
    /**
     * 获取查询结果
     *
     * @param builder
     * @param statement
     * @param parameter
     * @param rowBounds
     * @param resultHandler
     * @param executor
     * @param pageable
     * @return
     * @throws java.sql.SQLException
     */
    private List<?> getRecords(PageableBuilder builder,
                               MappedStatement statement,
                               BoundSql boundSql,
                               CacheKey cacheKey,
                               Object parameter,
                               RowBounds rowBounds,
                               ResultHandler resultHandler,
                               Executor executor,
                               Page<?> pageable) throws java.sql.SQLException {
        String selectSql = builder.buildPageableSql(boundSql.getSql(), pageable.offset(), pageable.getSize());
        SystemMetaObject.forObject(boundSql).setValue(BoundSqlFields.SQL, selectSql);
        return executor.query(statement, parameter, rowBounds, resultHandler, cacheKey, boundSql);
    }
    
    /**
     * 获取总数
     *
     *
     * @param builder
     * @param statement
     * @param parameter
     * @param rowBounds
     * @param resultHandler
     * @param executor
     * @return
     * @throws java.sql.SQLException
     */
    private long getTotal(PageableBuilder builder,
                          MappedStatement statement,
                          BoundSql boundSql,
                          Object parameter,
                          RowBounds rowBounds,
                          ResultHandler resultHandler,
                          Executor executor) throws java.sql.SQLException {
        Configuration configuration = statement.getConfiguration();
        String id = statement.getId() + "#count";
        
        if (configuration.hasStatement(id)) {
            statement = configuration.getMappedStatement(id);
        } else {
            String countSql = builder.buildCountSql(boundSql.getSql());
            SqlSource sqlSource = new StaticSqlSource(configuration, countSql, boundSql.getParameterMappings());
            ResultMap resultMap = new ResultMap.Builder(configuration,
                    id + "-Inline",
                    Long.class,
                    new ArrayList<>()).build();
            statement = new MappedStatement.Builder(configuration, id, sqlSource, SqlCommandType.SELECT)
                    .resultMaps(Collections.unmodifiableList(Arrays.asList(resultMap)))
                    .build();
        }
        List<Object> result = executor.query(statement, parameter, rowBounds, resultHandler);
        return result.isEmpty() ? 0L : ((Long) result.get(0));
    }
    
    /**
     * 获取分页参数
     *
     * @param parameter
     * @return
     */
    private Optional<Page<?>> getPageable(Object parameter) {
        
        // 多个函数参数
        if (parameter instanceof Map) {
            Map<String, Object> parameterMap = (Map<String, Object>) parameter;
            for (String k : parameterMap.keySet()) {
                Object v = parameterMap.get(k);
                if (v instanceof Page) {
                    return Optional.of((Page<?>) v);
                }
            }
        }
        // 单个函数参数
        else if (parameter instanceof Page) {
            return Optional.of((Page<?>) parameter);
        }
        return Optional.empty();
    }
}
