package in.hocg.squirrel.core.helper;

import com.google.common.collect.Maps;
import in.hocg.squirrel.exception.SquirrelException;

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
    
    /**
     * 实体类信息缓存 <Mapper类全名, 实体类>
     */
    private static final Map<String, Class<?>> ENTITY_CLASS_CACHE = Maps.newHashMap();
    
    
    /**
     * 从接口上获取实体的类型
     *
     * @param mapperClass
     * @return
     */
    public static Class<?> getEntityClass(Class<?> mapperClass) {
        String mapperClassName = mapperClass.getName();
        if (ENTITY_CLASS_CACHE.containsKey(mapperClassName)) {
            return ENTITY_CLASS_CACHE.get(mapperClassName);
        }
        return getMapperEntityClass(mapperClass);
    }
    
    /**
     * 从接口上获取实体的类型
     *
     * @param mapperClass
     * @return
     */
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
            throw SquirrelException.wrap("Mapper 接口文件: " + mapperClass + " 中找不到实体泛型");
        }
        
        Type[] actualTypeArguments = targetType.getActualTypeArguments();
        Class<?> entityClass = (Class<?>) actualTypeArguments[0];
        ENTITY_CLASS_CACHE.put(entityClass.getName(), entityClass);
        return entityClass;
    }
    
}
