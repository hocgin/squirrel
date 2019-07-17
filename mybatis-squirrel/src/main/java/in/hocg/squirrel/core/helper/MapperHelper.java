package in.hocg.squirrel.core.helper;

import java.lang.reflect.Method;

/**
 * Created by hocgin on 2019-07-18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class MapperHelper {
    
    /**
     * 通过函数名称获取 Mapper Class 对应的函数
     * @param methodName
     * @param mapperClass
     * @return
     */
    public static Method getMethod(String methodName, Class<?> mapperClass) {
        Method[] methods = mapperClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
    
        throw new RuntimeException("接口: " + mapperClass + "未找到函数" + methodName + "的映射");
    }
}
