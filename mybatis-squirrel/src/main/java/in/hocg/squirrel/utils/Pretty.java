package in.hocg.squirrel.utils;

import lombok.experimental.UtilityClass;

/**
 * Created by hocgin on 2019-07-28.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class Pretty {
    
    public static String sql(String sql) {
        return sql.replaceAll("[\\s]+", " ");
    }
}
