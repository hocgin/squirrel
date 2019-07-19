package in.hocg.squirrel.metadata;

import com.google.common.collect.Lists;
import in.hocg.squirrel.core.annotation.Id;
import in.hocg.squirrel.metadata.struct.Column;
import in.hocg.squirrel.metadata.struct.Table;
import in.hocg.squirrel.reflection.ClassKit;
import org.apache.ibatis.type.JdbcType;
import org.apache.logging.log4j.util.Strings;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * Created by hocgin on 2019-07-18.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
public class ColumnHelper {
    
    
    /**
     * 加载列结构
     *
     *
     * @param tableStruct
     * @param entityClass
     * @return
     */
    public static List<Column> loadColumnStruct(Table tableStruct, Class<?> entityClass) {
        List<Column> columns = Lists.newArrayList();
        
        List<Field> fields = ClassKit.from(entityClass).getAllField();
        for (Field field : fields) {
            Column columnStruct = getStruct(field);
    
            // 如果该字段是 @Id
            if (columnStruct.getIsPk()) {
                tableStruct.setKeyColumnName(columnStruct.getColumnName());
                tableStruct.setKeyFieldName(columnStruct.getFieldName());
            }
            columns.add(columnStruct);
        }
        return columns;
    }
    
    /**
     * 获取字段结构
     *
     * @param field
     * @return
     */
    public static Column getStruct(Field field) {
        return new Column()
                .setColumnName(getColumnName(field))
                .setJavaType(field.getType())
                .setFieldName(field.getName())
                .setJdbcType(getJdbcType(field))
                .setIsPk(isPk(field));
    }
    
    /**
     * 获取字段的 Java 类型
     *
     * @param field
     * @return
     */
    public static JdbcType getJdbcType(Field field) {
        JdbcType jdbcType = null;
        if (field.isAnnotationPresent(in.hocg.squirrel.core.annotation.Column.class)) {
            in.hocg.squirrel.core.annotation.Column column = field.getAnnotation(in.hocg.squirrel.core.annotation.Column.class);
            jdbcType = column.jdbcType();
        }
        return Objects.isNull(jdbcType) ? JdbcType.JAVA_OBJECT : jdbcType;
    }
    
    
    /**
     * 从字段上获取列名
     *
     * @param field
     * @return
     */
    public static String getColumnName(Field field) {
        String fieldName = null;
        if (field.isAnnotationPresent(in.hocg.squirrel.core.annotation.Column.class)) {
            in.hocg.squirrel.core.annotation.Column column = field.getAnnotation(in.hocg.squirrel.core.annotation.Column.class);
            fieldName = column.name();
        }
        return Strings.isBlank(fieldName) ? field.getName() : fieldName;
    }
    
    /**
     * 判断字段是否是主键
     *
     * @param field
     * @return
     */
    public static Boolean isPk(Field field) {
        return field.isAnnotationPresent(Id.class);
    }
}
