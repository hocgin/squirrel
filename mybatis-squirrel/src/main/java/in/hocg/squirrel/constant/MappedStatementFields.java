package in.hocg.squirrel.constant;

import lombok.experimental.UtilityClass;

/**
 * MappedStatement 内的属性名
 * <p>
 * Created by hocgin on 2019-07-23.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class MappedStatementFields {
    
    /**
     * 结果映射
     * List<ResultMap>
     */
    public static final String RESULT_MAPS = "resultMaps";
    
    /**
     * Sql
     * SqlSource
     */
    public static final String SQL_SOURCE = "sqlSource";
    
    /**
     * 主键属性字段
     * String[]
     */
    public static final String KEY_PROPERTIES = "keyProperties";
    
    /**
     * 主键列名
     * String[]
     */
    public static final String KEY_COLUMNS = "keyColumns";
    
    /**
     * 主键策略
     * KeyGenerator
     */
    public static final String KEY_GENERATOR = "keyGenerator";
}
