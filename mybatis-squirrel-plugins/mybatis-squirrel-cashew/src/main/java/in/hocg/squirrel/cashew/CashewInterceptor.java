package in.hocg.squirrel.cashew;

import in.hocg.squirrel.intercepts.AbstractInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

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
//        if (target instanceof Executor) {
//            return Plugin.wrap(target, this);
//        }
        return target;
    }
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }
}
