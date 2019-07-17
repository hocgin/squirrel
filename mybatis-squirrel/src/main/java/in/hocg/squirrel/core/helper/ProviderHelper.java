package in.hocg.squirrel.core.helper;

import in.hocg.squirrel.provider.BaseProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class ProviderHelper {
    
    public static Class<?> getProviderClass(Method method) {
        Class<?> providerClass = null;
        if (method.isAnnotationPresent(SelectProvider.class)) {
            providerClass = method.getAnnotation(SelectProvider.class).type();
        } else if (method.isAnnotationPresent(UpdateProvider.class)) {
            providerClass = method.getAnnotation(UpdateProvider.class).type();
        } else if (method.isAnnotationPresent(InsertProvider.class)) {
            providerClass = method.getAnnotation(InsertProvider.class).type();
        } else if (method.isAnnotationPresent(DeleteProvider.class)) {
            providerClass = method.getAnnotation(DeleteProvider.class).type();
        }
        return providerClass;
    }
    
    /**
     * 获取
     * @param statementId
     * @return
     */
    public static BaseProvider getMethodProvider(String statementId) {
        String methodName = MappedStatementHelper.getMethodName(statementId);
        Class<?> mapperClass = MappedStatementHelper.getMapperClass(statementId);
        
        Class<?> entityClass = EntityHelper.getEntityClass(mapperClass);
        
        Method method = MapperHelper.getMethod(methodName, mapperClass);
        
        Class<?> providerClass = ProviderHelper.getProviderClass(method);
        
        if (Objects.isNull(providerClass)) {
            throw new RuntimeException("该函数" + statementId + "没有实现方式");
        }
        
        BaseProvider provider = null;
        try {
            provider = (BaseProvider) providerClass.getConstructor(Class.class, Class.class, Method.class).newInstance(mapperClass, entityClass, method);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            log.error("创建 Provider(Class: {}, 参数: {}, {}, {}) 实例失败, 错误信息: {}", providerClass, mapperClass, entityClass, method, e);
        }
        
        return provider;
    }
}
