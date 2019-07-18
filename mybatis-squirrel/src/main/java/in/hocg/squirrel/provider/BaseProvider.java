package in.hocg.squirrel.provider;

import in.hocg.squirrel.core.helper.MappedStatementHelper;
import in.hocg.squirrel.core.helper.TableHelper;
import in.hocg.squirrel.core.table.ColumnStruct;
import in.hocg.squirrel.core.table.TableStruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 每个 @Provider 函数对应一个实例
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
@Data
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
    
    /**
     * 表结构
     */
    private final TableStruct tableStruct;
    /**
     * 列结构
     */
    private final List<ColumnStruct> columnStruct;
    
    public BaseProvider(Class<?> mapperClass, Class<?> entityClass, Method method) {
        this.mapperClass = mapperClass;
        this.entityClass = entityClass;
        this.method = method;
        this.tableStruct = TableHelper.getTableStruct(entityClass);
        this.columnStruct = TableHelper.getColumnStruct(entityClass);
        log.debug("");
    }
    
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
