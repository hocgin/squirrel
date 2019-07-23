package in.hocg.squirrel.builder;

import in.hocg.squirrel.core.Constants;
import lombok.NonNull;

/**
 * Created by hocgin on 2019-07-23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class SqlScripts {
    
    /**
     * xx = #{xx}
     *
     * @param column
     * @param parameterName
     * @return
     */
    public static String eq(@NonNull String column, @NonNull String parameterName) {
        return column + SqlKeyword.EQ.getValue() + parameter(parameterName);
    }
    
    /**
     * xx = #{id}
     *
     * @param keyProperties
     * @return
     */
    public static String idEq(@NonNull String keyProperties) {
        return eq(keyProperties, Constants.KEY_PARAMETER);
    }
    
    /**
     * 参数 #{xx}
     *
     * @param parameterName
     * @return
     */
    public static String parameter(@NonNull String parameterName) {
        return Constants.PARAMETER_PREFIX + parameterName + Constants.PARAMETER_SUFFIX;
    }
}
