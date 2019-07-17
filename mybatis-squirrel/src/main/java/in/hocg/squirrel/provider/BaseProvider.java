package in.hocg.squirrel.provider;

import in.hocg.squirrel.core.helper.MappedStatementHelper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 每个 @Provider 函数对应一个实例
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@RequiredArgsConstructor
public abstract class BaseProvider {
    
    /**
     * Mapper 类
     */
    private final Class<?> mapperClass;
    /**
     * Mapper 的实体
     */
    private final Class<?> entityClass;
    /**
     * 使用 Provider 的 Method
     */
    private final Method method;
    
    
    public String method() {
        return "SQL";
    }
    
    /**
     * 调用Provider处理器指定函数名的函数
     * @param mappedStatement
     */
    public void invokeProviderMethod(MappedStatement mappedStatement) {
        String methodName = MappedStatementHelper.getMethodName(mappedStatement.getId());
        invokeProviderMethod(methodName, mappedStatement);
    }
    
    /**
     * 调用Provider处理器指定函数名的函数
     * @param methodName
     * @param mappedStatement
     */
    private void invokeProviderMethod(String methodName, MappedStatement mappedStatement) {
        try {
            Method method = this.getClass().getMethod(methodName, MappedStatement.class);
            method.invoke(this, mappedStatement);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
