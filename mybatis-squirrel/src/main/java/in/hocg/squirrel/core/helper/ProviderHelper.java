package in.hocg.squirrel.core.helper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.lang.reflect.Method;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
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
}
