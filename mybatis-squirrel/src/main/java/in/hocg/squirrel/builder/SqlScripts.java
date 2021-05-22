package in.hocg.squirrel.builder;

import in.hocg.squirrel.constant.Constants;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Created by hocgin on 2019-07-23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class SqlScripts {

    /**
     * xx = #{xx}
     *
     * @param column column
     * @param parameterName parameterName
     * @return r
     */
    public static String eq(@NonNull String column, @NonNull String parameterName) {
        return column + SqlKeyword.EQ.getValue() + parameter(parameterName);
    }

    /**
     * xx = #{id}
     *
     * @param keyProperties keyProperties
     * @return r
     */
    public static String idEq(@NonNull String keyProperties) {
        return eq(keyProperties, Constants.KEY_PARAMETER);
    }

    /**
     * 参数 #{xx}
     *
     * @param parameterName parameterName
     * @return r
     */
    public static String parameter(@NonNull String parameterName) {
        return Constants.PARAMETER_PREFIX + parameterName + Constants.PARAMETER_SUFFIX;
    }
}
