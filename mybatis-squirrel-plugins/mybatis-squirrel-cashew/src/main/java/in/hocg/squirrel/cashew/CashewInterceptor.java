package in.hocg.squirrel.cashew;

import in.hocg.squirrel.Global;
import in.hocg.squirrel.intercepts.AbstractInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
//        ErrorContext.instance().activity("handling results").object(mappedStatement.getId());
//
//
//
//
        final List<Object> multipleResults = new ArrayList<>();

        int resultSetCount = 0;
        ResultSetWrapper rsw = getFirstResultSet(stmt);

//        List<ResultMap> resultMaps = mappedStatement.getResultMaps();
//        int resultMapCount = resultMaps.size();
//        validateResultMapsCount(rsw, resultMapCount);
//        while (rsw != null && resultMapCount > resultSetCount) {
//            ResultMap resultMap = resultMaps.get(resultSetCount);
//            handleResultSet(rsw, resultMap, multipleResults, null);
//            rsw = getNextResultSet(stmt);
//            cleanUpAfterHandlingResultSet();
//            resultSetCount++;
//        }
//
//        String[] resultSets = mappedStatement.getResultSets();
//        if (resultSets != null) {
//            while (rsw != null && resultSetCount < resultSets.length) {
//                ResultMapping parentMapping = nextResultMaps.get(resultSets[resultSetCount]);
//                if (parentMapping != null) {
//                    String nestedResultMapId = parentMapping.getNestedResultMapId();
//                    ResultMap resultMap = configuration.getResultMap(nestedResultMapId);
//                    handleResultSet(rsw, resultMap, null, parentMapping);
//                }
//                rsw = getNextResultSet(stmt);
//                cleanUpAfterHandlingResultSet();
//                resultSetCount++;
//            }
//        }
//
//        return collapseSingleResultList(multipleResults);
        log.debug("{}", "worked");
        return invocation.proceed();
    }
    
    
    
    private ResultSetWrapper getFirstResultSet(Statement stmt) throws SQLException {
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
        return rs != null ? new ResultSetWrapper(rs, Global.CONFIGURATION) : null;
    }
}
