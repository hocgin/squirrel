package in.hocg.squirrel.annotation;

import java.lang.annotation.*;

/**
 * Created by hocgin on 2019/7/12.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    /**
     * 表名
     *
     * @return
     */
    String name();
}
