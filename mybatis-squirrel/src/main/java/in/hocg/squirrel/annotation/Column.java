package in.hocg.squirrel.annotation;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

import java.lang.annotation.*;

/**
 * Created by hocgin on 2019/7/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    
    /**
     * 列名
     *
     * @return
     */
    String name();
    
    /**
     * 数据库类型
     *
     * @return
     */
    JdbcType jdbcType() default JdbcType.UNDEFINED;
    
    /**
     * 类型处理器
     *
     * @return
     */
    Class<? extends TypeHandler<?>> typeHandler() default UnknownTypeHandler.class;
}
