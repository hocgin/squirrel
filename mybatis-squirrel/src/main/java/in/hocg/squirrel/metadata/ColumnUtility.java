package in.hocg.squirrel.metadata;

import com.google.common.collect.Lists;
import in.hocg.squirrel.constant.Constants;
import in.hocg.squirrel.annotation.Id;
import in.hocg.squirrel.exception.SquirrelException;
import in.hocg.squirrel.metadata.struct.Column;
import in.hocg.squirrel.metadata.struct.Table;
import in.hocg.squirrel.utils.ClassUtility;
import in.hocg.squirrel.utils.LangUtility;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.keygen.KeyGenerator;
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
@Slf4j
@UtilityClass
public class ColumnUtility {
    
    
    /**
     * 加载列结构
     *
     * @param tableStruct
     * @param entityClass
     * @return
     */
    public static List<Column> loadColumnMetadata(Table tableStruct, Class<?> entityClass) {
        List<Column> columns = Lists.newArrayList();
        
        List<Field> fields = ClassUtility.from(entityClass).getAllField();
        for (Field field : fields) {
            Column column = getMetadata(field);
            
            // 如果该字段是 @Id
            if (column.getIsPk()) {
                tableStruct.setKeyColumnName(column.getColumnName());
                tableStruct.setKeyFieldName(column.getFieldName());
                tableStruct.setKeyGenerator(getAndNewKeyGenerator(field));
            }
            columns.add(column);
        }
        return columns;
    }
    
    /**
     * 获取并创建 KeyGenerator
     *
     * @param field
     * @return
     */
    private static KeyGenerator getAndNewKeyGenerator(Field field) {
        Class<? extends KeyGenerator> keyGenerator = getKeyGenerator(field);
        try {
            return keyGenerator.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("设置 {} 主键生成策略失败, 错误信息: {}", field.getName(), e);
            throw SquirrelException.wrap("设置 {fieldName} 主键生成策略失败", field.getName());
        }
    }
    
    /**
     * 从字段上的注解中获取主键生成策略
     *
     * @param field
     * @return
     */
    private static Class<? extends KeyGenerator> getKeyGenerator(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            Id id = field.getAnnotation(Id.class);
            return id.keyGenerator();
        }
        throw SquirrelException.wrap("{field} 上未找到 @Id 注解", field.getName());
    }
    
    /**
     * 获取字段结构
     *
     * @param field
     * @return
     */
    public static Column getMetadata(Field field) {
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
        if (field.isAnnotationPresent(in.hocg.squirrel.annotation.Column.class)) {
            in.hocg.squirrel.annotation.Column column = field.getAnnotation(in.hocg.squirrel.annotation.Column.class);
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
        if (field.isAnnotationPresent(in.hocg.squirrel.annotation.Column.class)) {
            in.hocg.squirrel.annotation.Column column = field.getAnnotation(in.hocg.squirrel.annotation.Column.class);
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
    
    /**
     * 获取所有列名
     *
     * @return
     */
    public static String[] getColumnNames(List<Column> columnStruct) {
        if (LangUtility.isEmpty(columnStruct)) {
            return new String[]{};
        }
        return columnStruct.stream().map(Column::getColumnName).toArray(String[]::new);
    }
    
    /**
     * 获取所有类型的参数名称
     *
     * @param columnStruct
     * @return
     */
    public static String[] getColumnParameters(List<Column> columnStruct) {
        if (LangUtility.isEmpty(columnStruct)) {
            return new String[]{};
        }
        return columnStruct.stream().map((c) -> Constants.BEAN_PARAMETER_PREFIX + c.getFieldName() + Constants.PARAMETER_SUFFIX).toArray(String[]::new);
    }
    
}
