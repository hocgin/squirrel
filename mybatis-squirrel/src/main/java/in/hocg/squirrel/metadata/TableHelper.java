package in.hocg.squirrel.metadata;

import in.hocg.squirrel.exception.SquirrelException;
import in.hocg.squirrel.metadata.struct.Column;
import in.hocg.squirrel.metadata.struct.Table;
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
public class TableHelper {
    
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
        Object columnStruct = COLUMN_CACHE.getObject(entityClassName);
        if (Objects.nonNull(columnStruct)) {
            return (List<Column>) columnStruct;
        }
        Table tableStruct = getTableStruct(entityClass);
        
        columnStruct = ColumnHelper.loadColumnStruct(tableStruct, entityClass);
        
        COLUMN_CACHE.putObject(entityClassName, columnStruct);
        
        return ((List<Column>) columnStruct);
    }
    
    /**
     * 解析实体类获取表结构
     *
     * @param entityClass
     * @return
     */
    public static Table getTableStruct(Class<?> entityClass) {
        String className = entityClass.getName();
        Object tableStruct = TABLE_CACHE.getObject(className);
        if (Objects.nonNull(tableStruct)) {
            return (Table) tableStruct;
        }
    
        tableStruct = loadTableStruct(entityClass);
        TABLE_CACHE.putObject(className, tableStruct);
        return ((Table) tableStruct);
    }
    
    /**
     * 加载实体的表结构
     *
     * @param entityClass
     * @return
     */
    private static Table loadTableStruct(Class<?> entityClass) {
        in.hocg.squirrel.core.annotation.Table table = entityClass.getAnnotation(in.hocg.squirrel.core.annotation.Table.class);
        if (Objects.isNull(table)) {
            throw SquirrelException.wrap("在 " + entityClass + " 未找到 @Table");
        }
    
        return new Table()
                .setTableName(table.name());
    }
}
