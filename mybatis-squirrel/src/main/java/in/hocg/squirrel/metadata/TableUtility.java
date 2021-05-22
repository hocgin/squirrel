package in.hocg.squirrel.metadata;

import in.hocg.squirrel.exception.SquirrelException;
import in.hocg.squirrel.metadata.struct.Column;
import in.hocg.squirrel.metadata.struct.Table;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.SoftCache;
import org.apache.ibatis.cache.impl.PerpetualCache;

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
public class TableUtility {

    /**
     * 表结构 <实体类全名, TableStruct>
     */
    private static final Cache TABLE_CACHE = new SoftCache(new PerpetualCache("TABLE_CACHE"));

    /**
     * 列缓存 <实体类全名, ColumnStruct>
     */
    private static final Cache COLUMN_CACHE = new SoftCache(new PerpetualCache("COLUMN_CACHE"));

    /**
     * 获取表结构
     *
     * @param entityClass
     */
    public static List<Column> getColumnStruct(Class<?> entityClass) {
        String entityClassName = entityClass.getName();
        Object column = COLUMN_CACHE.getObject(entityClassName);
        if (Objects.nonNull(column)) {
            return (List<Column>) column;
        }
        Table table = getTableMetadata(entityClass);

        column = ColumnUtility.loadColumnMetadata(table, entityClass);

        COLUMN_CACHE.putObject(entityClassName, column);

        return ((List<Column>) column);
    }

    /**
     * 解析实体类获取表结构
     *
     * @param entityClass
     * @return r
     */
    public static Table getTableMetadata(Class<?> entityClass) {
        String className = entityClass.getName();
        Object table = TABLE_CACHE.getObject(className);
        if (Objects.nonNull(table)) {
            return (Table) table;
        }

        table = loadTableMetadata(entityClass);
        TABLE_CACHE.putObject(className, table);
        return ((Table) table);
    }

    /**
     * 加载实体的表结构
     *
     * @param entityClass
     * @return r
     */
    private static Table loadTableMetadata(Class<?> entityClass) {
        in.hocg.squirrel.annotation.Table table = entityClass.getAnnotation(in.hocg.squirrel.annotation.Table.class);
        if (Objects.isNull(table)) {
            throw SquirrelException.wrap("在 {entityClass} 未找到 @Table", entityClass);
        }

        return new Table().setTableName(table.name());
    }
}
