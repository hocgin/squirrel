package in.hocg.squirrel.intercepts;


import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;

/**
 * Created by hocgin on 2019-07-25.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public abstract class AbstractInterceptor implements Interceptor {
    
    /**
     * 获取 Invocation 的 target
     *
     * @param target
     * @param <T>
     * @return
     */
    protected <T> T getTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return getTarget(metaObject.getValue("h.target"));
        }
        return (T) target;
    }
    
}
