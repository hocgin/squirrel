package in.hocg.squirrel.core.helper;

import com.google.common.collect.Maps;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

/**
 * Created by hocgin on 2019-07-17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class EntityHelper {
    private static final Map<String, Class<?>> ENTITY_CLASS_CACHE = Maps.newHashMap();
    
    
    public static Class<?> getEntityClass(Class<?> mapperClass) {
        String mapperClassName = mapperClass.getName();
        if (ENTITY_CLASS_CACHE.containsKey(mapperClassName)) {
            return ENTITY_CLASS_CACHE.get(mapperClassName);
        }
        return getMapperEntityClass(mapperClass);
    }

    public static Class<?> getMapperEntityClass(Class<?> mapperClass) {
        Type[] types = mapperClass.getGenericInterfaces();
        ParameterizedType targetType = null;
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                targetType = ((ParameterizedType) type);
                break;
            }
        }
        
        if (Objects.isNull(targetType)) {
            throw new RuntimeException("找不到泛型参数");
        }
    
        Type[] actualTypeArguments = targetType.getActualTypeArguments();
        Class<?> entityClass = (Class<?>) actualTypeArguments[0];
        ENTITY_CLASS_CACHE.put(entityClass.getName(), entityClass);
        return entityClass;
    }

}
