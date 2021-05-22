package in.hocg.squirrel.annotation;

import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;

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
public @interface Id {

    /**
     * 主键生成策略
     *
     * @return r
     */
    Class<? extends KeyGenerator> keyGenerator() default NoKeyGenerator.class;
}
