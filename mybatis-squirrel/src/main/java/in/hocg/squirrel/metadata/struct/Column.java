package in.hocg.squirrel.metadata.struct;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.ibatis.type.UnknownTypeHandler;
import org.apache.logging.log4j.util.Strings;

import static in.hocg.squirrel.constant.Constants.COMMA;

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
    private Class<? extends TypeHandler<?>> typeHandler;
    
    /**
     * 指定小数点后保留的位数
     *
     * @return
     */
    private String numericScale;
    
    /**
     * 是否是主键
     */
    private Boolean isPk = false;
    
    /**
     * 获取参数 EL 形式
     * @return
     */
    public String getEl() {
        String el = this.fieldName;
        if (JdbcType.UNDEFINED != jdbcType) {
            el += (COMMA + "jdbcType=" + jdbcType.name());
        }
        if (UnknownTypeHandler.class != typeHandler) {
            el += (COMMA + "typeHandler=" + typeHandler.getName());
        }
        if (Strings.isNotEmpty(numericScale)) {
            el += (COMMA + "numericScale=" + numericScale);
        }
        return el;
    }
    
    /**
     * 获取结果映射规则
     *
     * @param configuration
     * @return
     */
    public ResultMapping getResultMapping(final Configuration configuration) {
        ResultMapping.Builder builder = new ResultMapping.Builder(configuration, fieldName,
                columnName, javaType);
        TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
        if (jdbcType != null && jdbcType != JdbcType.UNDEFINED) {
            builder.jdbcType(jdbcType);
        }
        if (typeHandler != null && typeHandler != UnknownTypeHandler.class) {
            TypeHandler<?> typeHandler = registry.getMappingTypeHandler(this.typeHandler);
            if (typeHandler == null) {
                typeHandler = registry.getInstance(javaType, this.typeHandler);
                // todo 这会有影响 registry.register(typeHandler);
            }
            builder.typeHandler(typeHandler);
        }
        return builder.build();
    }
}
