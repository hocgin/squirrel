package in.hocg.squirrel.helper;

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
        return ENTITY_CLASS_CACHE.computeIfAbsent(mapperClass.getName(), (key) -> getMapperEntityClass(mapperClass));
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
            throw SquirrelException.wrap("Mapper 接口文件: {} 中找不到实体泛型", mapperClass);
        }
        
        Type[] actualTypeArguments = targetType.getActualTypeArguments();
        return (Class<?>) actualTypeArguments[0];
    }
    
}
