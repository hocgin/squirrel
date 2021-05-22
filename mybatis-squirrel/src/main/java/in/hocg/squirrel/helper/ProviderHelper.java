package in.hocg.squirrel.helper;

import in.hocg.squirrel.exception.SquirrelException;
import in.hocg.squirrel.provider.AbstractProvider;
import in.hocg.squirrel.utils.ClassUtility;
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

    /**
     * 获取函数上面的 Provider.class 注解
     *
     * @param method
     * @return r
     */
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
     * 通过 statementId 获取 BaseProvider
     *
     * @param statementId
     * @return r
     */
    public static AbstractProvider getMethodProvider(String statementId) {
        String methodName = MappedStatementHelper.getMethodName(statementId);
        Class<?> mapperClass = MappedStatementHelper.getMapperClass(statementId);

        Class<?> entityClass = EntityHelper.getEntityClass(mapperClass);

        Method method = ClassUtility.from(mapperClass).getMethod(methodName);

        Class<?> providerClass = ProviderHelper.getProviderClass(method);

        if (Objects.isNull(providerClass)) {
            throw SquirrelException.wrap("该函数 {}{} 没有对应的 Provider 实现", mapperClass.getName(), methodName);
        }

        AbstractProvider provider;
        try {
            provider = (AbstractProvider) providerClass.getConstructor(Class.class, Class.class, Method.class)
                    .newInstance(mapperClass, entityClass, method);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("创建 Provider(Class: {}, 参数: {}, {}, {}) 实例失败, 错误信息: {}", providerClass, mapperClass, entityClass, method, e);
            throw SquirrelException.wrap("获取 Provider 失败，Statement Id: {}, 参数: {}, {}, {}", statementId, mapperClass, entityClass, method);
        }

        return provider;
    }
}
