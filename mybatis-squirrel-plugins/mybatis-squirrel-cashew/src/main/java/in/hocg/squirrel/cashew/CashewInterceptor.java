package in.hocg.squirrel.cashew;

import in.hocg.squirrel.intercepts.AbstractInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by hocgin on 2019-08-21.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class CashewInterceptor extends AbstractInterceptor {
    
    
    @Override
    public Object plugin(Object target) {
        if (target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Statement stmt = (Statement) invocation.getArgs()[0];
        ResultSet rs = getFirstResultSet(stmt);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        boolean isNeedHandle = false;
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = rs.getObject(i);
                log.debug("{} => {}: '{}'", i, columnName, value);
                
                
                if (columnName.contains(Constants.CASHEW_SEPARATOR)) {
                    isNeedHandle = true;
                    
                }
            }
        }
        Object proceed = invocation.proceed();
        
        
        if (!isNeedHandle) {
            return proceed;
        }


        return proceed;
    }
    
    private ResultSet getFirstResultSet(Statement stmt) throws SQLException {
        ResultSet rs = stmt.getResultSet();
        while (rs == null) {
            // move forward to get the first resultset in case the driver
            // doesn't return the resultset as the first result (HSQLDB 2.1)
            if (stmt.getMoreResults()) {
                rs = stmt.getResultSet();
            } else {
                if (stmt.getUpdateCount() == -1) {
                    // no more results. Must be no resultset
                    break;
                }
            }
        }
        return rs;
    }
}
