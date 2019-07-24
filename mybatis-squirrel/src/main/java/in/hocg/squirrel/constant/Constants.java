package in.hocg.squirrel.constant;

import lombok.experimental.UtilityClass;

/**
 * Created by hocgin on 2019-07-19.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class Constants {
    public static final String COMMA = ".";
    
    /**
     * 主键参数名
     */
    public static final String KEY_PARAMETER = "id";
    
    /**
     * 数组参数名
     */
    public static final String ARRAY_PARAMETER = "array";
    
    /**
     * 实体参数名
     */
    public static final String BEAN_PARAMETER = "bean";
    
    
    /**
     * 参数前缀: #{bean.
     */
    public static final String BEAN_PARAMETER_PREFIX = Constants.PARAMETER_PREFIX + Constants.BEAN_PARAMETER + Constants.COMMA;
    
    /**
     * 参数前缀
     */
    public static final String PARAMETER_PREFIX = "#{";
    /**
     * 参数后缀
     */
    public static final String PARAMETER_SUFFIX = "}";
}
