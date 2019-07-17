package in.hocg.squirrel.sample.module;

import in.hocg.squirrel.sample.module.mapper.ExampleMapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Created by hocgin on 2019-07-17.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Slf4j
public class Main {
    
    
    public static void main(String[] args) {
        Type[] types = ExampleMapper.class.getGenericInterfaces();
        ParameterizedType targetType = null;
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                targetType = ((ParameterizedType) type);
                break;
            }
        }
        if (Objects.isNull(targetType)) {
            throw new RuntimeException("targetType is NULL");
        }
        
        Type[] actualTypeArguments = targetType.getActualTypeArguments();
    
        log.debug("{}", actualTypeArguments);
    }
}
