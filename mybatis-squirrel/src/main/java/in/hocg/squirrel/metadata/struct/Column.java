package in.hocg.squirrel.metadata.struct;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * Created by hocgin on 2019-07-18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@Data
@Accessors(chain = true)
public class Column {
    
    /**
     * 列名
     */
    private String columnName;
    
    /**
     * 字段名
     */
    private String fieldName;
    
    /**
     * Java 类型
     */
    private Class<?> javaType;
    
    /**
     * Jdbc 类型
     */
    private JdbcType jdbcType;
    
    /**
     * 类型处理器
     */
    private TypeHandler<?> typeHandler;
    
    /**
     * 是否是主键
     */
    private Boolean isPk = false;
    
}
