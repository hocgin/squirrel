package in.hocg.squirrel.intercepts.typehandle;

import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.*;

/**
 * Created by hocgin on 2019-09-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CustomTypeHandle {
    /**
     * 类型处理器
     *
     * @return r
     */
    Class<? extends TypeHandler<?>> typeHandler() default UnknownTypeHandler.class;
}
